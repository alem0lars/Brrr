###
# Plugins/Extensions configurations
###

# Enable HTML5 generation by default
set :haml, format: :html5

# Automatic image dimensions on image_tag helper
activate :automatic_image_sizes

# Reload the browser automatically whenever files change
activate :livereload

# Enable zurb foundation
require "zurb-foundation"

# Be verbose on compass
compass_config do |config|
  config.output_style = :expanded
end


###
# Page options, layouts, aliases and proxies
###

# INF: Empty


###
# Helpers
###


###
# Directories
###

set :css_dir, '/assets/styles'

set :js_dir, '/assets/scripts'

set :images_dir, '/assets/images'

set :source_dir, '/source'

###
# Build-specific configuration
###

configure :build do

  activate :minify_css
  activate :minify_javascript
  activate :cache_buster
  # compress `png`s after build
  require "middleman-smusher"
  activate :smusher
  # create `favicon/touch` icon set from `source/favicon_base.png`
  #activate :favicon_maker

end
