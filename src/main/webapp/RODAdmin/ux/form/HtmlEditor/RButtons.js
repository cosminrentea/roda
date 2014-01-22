Ext.define('Ext.ux.form.HtmlEditor.RButtons', {
	mixins : {
		observable : 'Ext.util.Observable'
	},
	// alternateClassName: 'Ext.form.plugin.HtmlEditor',
	requires : ['Ext.tip.QuickTipManager', 'Ext.form.field.HtmlEditor'],
	constructor : function(config) {
		Ext.apply(this, config);
	},

	init : function(editor) {
		var me = this;
		me.editor = editor;
		me.toolbar = null;
		me.mon(editor, 'initialize', me.onInitialize, me);
		me.mon(editor, 'sync', me.updateToolbar, me);
		// me.mon(editor, 'editmodechange', me.onEditorModeChanged, me);
	},

	onInitialize : function() {
		var me = this, undef, items = [], baseCSSPrefix = Ext.baseCSSPrefix, tipsEnabled = Ext.tip.QuickTipManager
				&& Ext.tip.QuickTipManager.isEnabled();
		function btn(id, toggle, handler) {
			var button = {
				itemId : id,
				cls : baseCSSPrefix + 'btn-icon',
				iconCls : baseCSSPrefix + 'edit-' + id,
				enableToggle : toggle !== false,
				scope : me,
				handler : handler || me.relayBtnCmd,
				clickEvent : 'mousedown',
				tooltip : tipsEnabled ? me.buttonTips[id] || undef : undef,
				overflowText : me.buttonTips[id].title || undef,
				tabIndex : -1
			};
			return button;
		}
		// push buttons here
		items.push(btn('insertCollateral', false, me.doInsertCollateral));
		if (items.length > 0) {
			me.getToolbar().add(items);
			fn = Ext.Function.bind(me.onEditorEvent, me);
			Ext.EventManager.on(me.editor.getDoc(), {
						mousedown : fn,
						dblclick : fn,
						click : fn,
						keyup : fn,
						buffer : 100
					});
		}
	},

	getToolbar : function() {
		return this.editor.getToolbar();
	},

	onEditorEvent : function(e) {
		var me = this, diffAt = 0;
		me.curLength = me.editor.getValue().length;
		me.currPos = me.getSelectionNodePos();
		me.currNode = me.getSelectionNode();

		if (e.ctrlKey) {
			var c = e.getCharCode();
			if (c > 0) {
				c = String.fromCharCode(c);
				if (c.toLowerCase() == 'v' && me.wordPasteEnabled) {
					me.cleanWordPaste();
				}
			}
		}

		me.lastLength = me.editor.getValue().length;
		me.lastValue = me.editor.getValue();
		me.lastPos = me.getSelectionNodePos();
		me.lastNode = me.getSelectionNode();

	},

	updateToolbar : function() {
		var me = this, btns, doc;

		if (me.editor.readOnly) {
			return;
		}

		btns = Ext.Object.merge(me.getToolbar().items.map, me.editor
						.getToolbar().items.map);
		doc = me.editor.getDoc();

		function updateButtons() {
			Ext.Array.forEach(Ext.Array.toArray(arguments), function(name) {
						if (btns[name]) {
							btns[name].toggle(doc.queryCommandState(name));
						}
					});
		}
	},

	getSelectionNodePos : function() {
		return this.getSelection().getRangeAt(0).startOffset;
	},

	getSelection : function() {
		if (this.editor.getWin().getSelection) {
			return this.editor.getWin().getSelection();
		} else if (this.editor.getDoc().getSelection) {
			return this.editor.getDoc().getSelection();
		} else if (this.editor.getDoc().selection) {
			return this.editor.getDoc().selection;
		}
	},

	getSelectionNode : function() {
		var currNode;
		if (this.editor.getWin().getSelection) {
			currNode = this.editor.getWin().getSelection().focusNode;
		} else if (this.editor.getDoc().getSelection) {
			currNode = this.editor.getDoc().getSelection().focusNode;
		} else if (this.editor.getDoc().selection) {
			currNode = this.editor.getDoc().selection.createRange()
					.parentElement();
		}

		return currNode;
	},

	doInsertCollateral : function() {
		// Table language text
		var me = this, showCellLocationText = false;

		if (!me.collateralWindow) {
			var mystore = Ext.create('RODAdmin.store.cms.Collateral');
			mystore.load();
			me.collateralWindow = new Ext.Window({
						title : me.buttonTips['insertCollateral'].title,
						closeAction : 'hide',
						layout : {
							type : 'fit',
							align : 'stretch'
						},
						modal : true,
						width : '635px',
						height : '700px',
						items : [{
							xtype : 'treepanel',
							itemId : 'collaterals',
							flex : 3,

							itemSelector : 'div.thumb-wrap',
							store : mystore,
							features : [Ext.create(
									'Ext.ux.grid.FiltersFeature', {
										local : true
									})],
							columns : [{
										text : 'Name',
										flex : 1,
										dataIndex : 'name',
										xtype : 'treecolumn',
										itemId : 'ft'
									}, {
										text : 'Type',
										flex : 1,
										dataIndex : 'type',
										sortable : true,
										filterable : true
									}, {
										text : 'Description',
										flex : 1,
										dataIndex : 'description',
										sortable : true,
										filterable : true
									}

							]
							,
						}],

						buttons : [{
							text : me.buttonTips['insertCollateral'].insert,
							handler : function() {
								var frow = this.collateralWindow.down('treepanel')
										.getSelectionModel().getLastSelected();

								if (frow) {
									var html;
									if (frow.data.type == 'file') {
										html = "{{FILE: " + frow.data.name
												+ "}}";
									} else if (frow.data.type == 'snippet') {
										html = "{{SNIPPET: " + frow.data.name
												+ "}}";
									} else if (frow.data.type == 'action') {
										html = "{{ACTION: " + frow.data.name
												+ "}}";
									}
									if (html.length > 0) {
										// Workaround, if the editor is
										// currently not in focus
										var before = this.editor.getValue();
										this.editor.insertAtCursor(html);
										var after = this.editor.getValue();
										if (before == after) {
											this.editor.setValue(before + html);
										}
										this.collateralWindow.hide();
									}
								}
							},
							scope : this
						}, {
							text : me.buttonTips['insertCollateral'].cancel,
							handler : function() {
								this.collateralWindow.hide();
							},
							scope : this
						}]
					}).show();
		} else {
			// this.tableWindow.down('form').getForm().reset();
			this.collateralWindow.show();
		}
	},

	relayBtnCmd : function(btn) {
		this.relayCmd(btn.getItemId());
	},

	relayCmd : function(cmd, value) {
		Ext.defer(function() {
			var me = this;
			me.editor.focus();
			me.editor.execCmd(cmd, value);
				// me.updateToolbar();
			}, 10, this);
	},

	buttonTips : {
		insertCollateral : {
			title : 'Insert Collateral',
			text : 'Insert collateral code.',
			insert : 'Insert selected collateral',
			cancel: 'close window',
			cls : Ext.baseCSSPrefix + 'html-editor-tip'
		}
	}

})
