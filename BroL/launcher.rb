require "ostruct"
require "pathname"
require "fileutils"
require "yaml"


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
    init_presets(ARGV)
    init_external_args(ARGV)
  end

  def init_presets(args)
    
    file = File.open($paths.root.join("presets.yml"), "r")
    presets = YAML::load(file.read)
    file.close

    current_preset_arg = args.find {|arg| arg.start_with?("--preset=")}

    unless current_preset_arg.nil?

      current_preset_name = current_preset_arg.gsub("--preset=", "")
      configs = presets.find do |preset_name, preset_configs|
        preset_name == current_preset_name
      end
      if configs.nil?
        puts "Preset #{current_preset_name} hasn't been configured yet"
        exit(-2)
      end
      selected_configs = configs[1].map{ |config_name| "#{$paths.configs.join(config_name)}.bro" }

      @cmd << " #{selected_configs.join(' ')}" if selected_configs.length > 0

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
      puts `#{@cmd}`
    end
  end

end


Launcher.new.run
