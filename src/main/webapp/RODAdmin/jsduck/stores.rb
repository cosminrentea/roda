require "jsduck/tag/class_list_tag"

module JsDuck::Tag
  class Stores < ClassListTag
    def initialize
      @pattern = "storess"
      @tagname = :stores
      @repeatable = true
      @ext_define_pattern = "stores"
      @ext_define_default = {:stores => []}
    end

    def classname_list(p)
      classes = []
      Kernel::exit("Come on")
      while cls = p.hw.ident_chain
        classes << "RODAdmin.store." + cls
      end
      classes
    end


  end
end
