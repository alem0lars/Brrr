require "ostruct"
require "pathname"
require "fileutils"
require "yaml"
require "open3"


# { Paths

$paths = OpenStruct.new
$paths.root = Pathname.new(File.dirname __FILE__).expand_path
$paths.configs = $paths.root.join("configs")
$paths.tmp = $paths.root.join("tmp")

FileUtils.mkdir_p($paths.tmp) unless $paths.tmp.directory?
FileUtils.mkdir_p($paths.configs) unless $paths.configs.directory?

# }


class Launcher

  def initialize
    @cmd = "bro -b"
    @selected_configs = []
    init_global_bro(ARGV)
    init_presets(ARGV)
    init_external_args(ARGV)
  end

  def init_global_bro(args)

    global_bro_path = $paths.tmp.join("global.bro")
    content = ""

    file = File.open($paths.root.join("global_config.yml"), "r")
    global_configs = YAML::load(file.read)
    file.close

    if global_configs

      if global_configs.has_key?("local_nets")
        local_nets = global_configs["local_nets"]
        unless [String, Array].any? { |klass| klass === local_nets }
          raise "Invalid local_nets"
        end

        content << "redef Site::load_nets += { #{local_nets.join(', ')} };\n"
      end

      if global_configs.has_key?("ignore_checksums")
        ignore_checksums = global_configs["ignore_checksums"]
        unless [TrueClass, FalseClass].any? { |klass| klass === ignore_checksums }
          raise "Invalid ignore_checksums"
        end

        content << "redef ignore_checksums = #{ignore_checksums ? 'T' : 'F'};"
      end

      if global_configs.has_key?("interface")
        interface = global_configs["interface"]
        unless [String].any? { |klass| klass === interface }
          raise "Invalid interface"
        end

        @cmd << " -i #{interface}"
      else
        raise "No network interface specified"
      end

      if content.length > 0
        File.open(global_bro_path, "w") { |f| f.write(content) }
      end

    end

    @selected_configs << global_bro_path if global_bro_path.exist?

  end

  def init_presets(args)

    file = File.open($paths.root.join("presets.yml"), "r")
    presets = YAML::load(file.read)
    file.close

    current_preset_arg = args.find {|arg| arg.start_with?("--preset=")}

    unless current_preset_arg.nil?

      current_preset_elems = []

      current_preset_name = current_preset_arg.gsub("--preset=", "")
      current_preset_elems = presets.find do |preset_name, _|
        preset_name == current_preset_name
      end
      if current_preset_elems.nil? # Validate - step 1
        puts "Preset #{current_preset_name} hasn't been configured yet"
        exit(-2)
      elsif !(Array === current_preset_elems) # Validate - step 2
        raise "The preset elements should be a list"
      else # Fix
        current_preset_elems = current_preset_elems[1].flatten.uniq
      end

      @selected_configs += current_preset_elems.collect do |preset_elem|

        # If the preset configuration hasn't 'bundled', provide a default
        preset_elem["bundled"] ||= false

        # Compute selected config
        preset_elem["bundled"] ?
            preset_elem["name"] :
            "#{$paths.configs.join(preset_elem["name"])}.bro"
      end

      @cmd << " #{@selected_configs.join(' ')}" if @selected_configs.length > 0

    end

  end

  def init_external_args(args)
    external_args = args.dup
    external_args.delete_if { |arg| arg.start_with?("--preset=") }
    @cmd << " #{external_args.join(' ')}" if external_args.length > 0
  end

  def run
    FileUtils.cd($paths.tmp) do
      puts "Launching: #{@cmd}"

      Open3.popen3(@cmd) { |stdin, stdout, stderr, wait_thr|
        pid = wait_thr.pid # pid of the started process.

        # TODO: poll stdout and stderr
        while line = stdout.gets
          puts line
        end

        exit_status = wait_thr.value # Process::Status object returned.
      }
    end
  end

end


Launcher.new.run
