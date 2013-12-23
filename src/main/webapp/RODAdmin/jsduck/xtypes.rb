require "jsduck/tag/member_tag"
require "jsduck/params_merger"

module JsDuck::Tag
  class Xtypes < MemberTag
    def initialize
      @pattern = "xtypes"
      @tagname = :xtypes
      @member_type = {
        :title => "Xtypes",
        :position => MEMBER_POS_EVENT,
        :icon => File.dirname(__FILE__) + "/icons/event.png"
      }
    end

    # @event name ...
    def parse_doc(p, pos)
      {
        :tagname => :xtypes,
        :name => p.ident,
      }
    end

    def process_doc(h, tags, pos)
      h[:name] = tags[0][:name]
    end

#    def merge(h, docs, code)
#      JsDuck::ParamsMerger.merge(h, docs, code)
#    end

    def to_html(xtypes, cls)
      member_link(xtypes) + member_params(xtypes[:params])
    end
  end
end
