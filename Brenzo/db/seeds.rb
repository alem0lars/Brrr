# Every Brenzo instance should have one undefined service: create it.
service_undefined = Service.find_or_create_by(
    name: Service::UNDEFINED_NAME,
    info: "It's mainly used for the uncategorized netflows")
Rails.logger.debug("Created the service undefined: #{service_undefined}")
