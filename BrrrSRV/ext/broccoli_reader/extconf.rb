# Loads mkmf which is used to make makefiles for Ruby extensions
require 'mkmf'


# { Add LDFlags

[
	'-lm',
	`broccoli-config --libs`,
  	`pkg-config --libs json`
].each { |ldflags| $LDFLAGS << ' ' + ldflags.chomp }

# }


# { Add CFlags

[
	'-g',
	'-Wall',
	`broccoli-config --cflags`,
  	`pkg-config --cflags json`
].each { |cflags| $CFLAGS << ' ' + cflags.chomp }

# }
puts $CFLAGS

# Give it a name
extension_name = 'broccoli_reader'

# The destination
# dir_config(extension_name)

# create_header()
create_makefile(extension_name)
