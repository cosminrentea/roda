require "jsduck/tag/member_tag"
require "jsduck/params_merger"

module JsDuck::Tag
  class Todo < MemberTag
    def initialize
      @pattern = "todo"
      @tagname = :todo
      @repeatable = true
      @member_type = {
        :title => "To do's",
        :position => MEMBER_POS_EVENT,
        :icon => File.dirname(__FILE__) + "/icons/event.png"
      }
    end

    # @event name ...
    def parse_doc(p, pos)
      {
        :tagname => :todo,
        :name => p.ident,
      }
    end

    def process_doc(h, tags, pos)
      h[:name] = tags[0][:name]
    end

    def merge(h, docs, code)
      JsDuck::ParamsMerger.merge(h, docs, code)
    end

    def to_html(todo, cls)
#      member_link(todo) + member_params(todo[:params])
      member_link(todo)
    end
  end
end
