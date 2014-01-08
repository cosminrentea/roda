require "jsduck/tag/class_list_tag"

module JsDuck::Tag
  class Views < ClassListTag
    def initialize
      @pattern = "views"
      @tagname = :views
      @repeatable = true
      @ext_define_pattern = "views"
      @ext_define_default = {:views => []}
    end
  end
end
