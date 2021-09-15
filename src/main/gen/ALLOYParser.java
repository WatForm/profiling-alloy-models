// Generated from C:/Users/eeid/IdeaProjects/alloy-profiling/src/main/antlr/com/alloyprofiling\ALLOY.g4 by ANTLR 4.9.1

 package com.alloyprofiling;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ALLOYParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, T__53=54, T__54=55, T__55=56, T__56=57, T__57=58, T__58=59, 
		T__59=60, T__60=61, T__61=62, T__62=63, T__63=64, T__64=65, T__65=66, 
		T__66=67, T__67=68, T__68=69, T__69=70, T__70=71, T__71=72, T__72=73, 
		T__73=74, T__74=75, T__75=76, T__76=77, T__77=78, T__78=79, T__79=80, 
		DIGIT=81, ID=82, ALPHA=83, WS=84, COMMENT=85, LINE_COMMENT=86, STRING=87;
	public static final int
		RULE_specification = 0, RULE_module = 1, RULE_names_opt = 2, RULE_as_name_opt = 3, 
		RULE_para_open = 4, RULE_open = 5, RULE_macro_expr = 6, RULE_macro = 7, 
		RULE_paragraph = 8, RULE_factDecl = 9, RULE_name_opt = 10, RULE_assertDecl = 11, 
		RULE_nameID = 12, RULE_decls_f = 13, RULE_funExpr = 14, RULE_funDecl = 15, 
		RULE_nameOrBlock = 16, RULE_name_cmd_opt = 17, RULE_scope_opt = 18, RULE_run_or_check = 19, 
		RULE_cmdDecl = 20, RULE_paraDecls = 21, RULE_paraDecls_opt = 22, RULE_predDecl = 23, 
		RULE_typescopes = 24, RULE_but_typescopes = 25, RULE_expect_digit = 26, 
		RULE_scope = 27, RULE_exactly_opt = 28, RULE_typescope = 29, RULE_decls = 30, 
		RULE_multiplicity = 31, RULE_abs = 32, RULE_priv = 33, RULE_abs_multiplicity = 34, 
		RULE_sigDecl = 35, RULE_names = 36, RULE_enumDecl = 37, RULE_mult = 38, 
		RULE_union = 39, RULE_superSet = 40, RULE_sigExt = 41, RULE_sigExtension = 42, 
		RULE_exprs = 43, RULE_notOp = 44, RULE_decls_e = 45, RULE_expr = 46, RULE_num = 47, 
		RULE_const_sets = 48, RULE_constant = 49, RULE_disjFunc = 50, RULE_disjoint = 51, 
		RULE_disj = 52, RULE_comma_opt = 53, RULE_decl = 54, RULE_letDecl = 55, 
		RULE_quant = 56, RULE_setCard = 57, RULE_tcOp = 58, RULE_unOp = 59, RULE_bit_shifter_operators = 60, 
		RULE_dotOp = 61, RULE_add = 62, RULE_sub = 63, RULE_binOp = 64, RULE_mult_or_set = 65, 
		RULE_arrowOp = 66, RULE_rel_operators = 67, RULE_compareOp = 68, RULE_block = 69, 
		RULE_block_opt = 70, RULE_blockOrBar = 71, RULE_bar = 72, RULE_name = 73, 
		RULE_number = 74;
	private static String[] makeRuleNames() {
		return new String[] {
			"specification", "module", "names_opt", "as_name_opt", "para_open", "open", 
			"macro_expr", "macro", "paragraph", "factDecl", "name_opt", "assertDecl", 
			"nameID", "decls_f", "funExpr", "funDecl", "nameOrBlock", "name_cmd_opt", 
			"scope_opt", "run_or_check", "cmdDecl", "paraDecls", "paraDecls_opt", 
			"predDecl", "typescopes", "but_typescopes", "expect_digit", "scope", 
			"exactly_opt", "typescope", "decls", "multiplicity", "abs", "priv", "abs_multiplicity", 
			"sigDecl", "names", "enumDecl", "mult", "union", "superSet", "sigExt", 
			"sigExtension", "exprs", "notOp", "decls_e", "expr", "num", "const_sets", 
			"constant", "disjFunc", "disjoint", "disj", "comma_opt", "decl", "letDecl", 
			"quant", "setCard", "tcOp", "unOp", "bit_shifter_operators", "dotOp", 
			"add", "sub", "binOp", "mult_or_set", "arrowOp", "rel_operators", "compareOp", 
			"block", "block_opt", "blockOrBar", "bar", "name", "number"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'module'", "'['", "','", "'exactly'", "']'", "'as'", "'open'", 
			"'='", "'{'", "'}'", "'let'", "'fact'", "'assert'", "'.'", "'fun'", "':'", 
			"'run'", "'check'", "'('", "')'", "'pred'", "'but'", "'expect'", "'for'", 
			"'seq'", "'int'", "'abstract'", "'private'", "'sig'", "'enum'", "'lone'", 
			"'one'", "'some'", "'+'", "'extends'", "'in'", "'!'", "'not'", "'=>'", 
			"'implies'", "'else'", "'seq/Int'", "'@'", "'this'", "'-'", "'none'", 
			"'univ'", "'iden'", "'disj'", "'disjoint'", "'all'", "'no'", "'sum'", 
			"'#'", "'*'", "'^'", "'set'", "'~'", "'<<'", "'>>'", "'>>>'", "'||'", 
			"'or'", "'&&'", "'and'", "'<=>'", "'iff'", "'&'", "'++'", "'<:'", "':>'", 
			"'->'", "'<'", "'>'", "'=<'", "'>='", "'<='", "'|'", "'this/'", "'/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "DIGIT", "ID", 
			"ALPHA", "WS", "COMMENT", "LINE_COMMENT", "STRING"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ALLOY.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ALLOYParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class SpecificationContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ALLOYParser.EOF, 0); }
		public List<ModuleContext> module() {
			return getRuleContexts(ModuleContext.class);
		}
		public ModuleContext module(int i) {
			return getRuleContext(ModuleContext.class,i);
		}
		public List<OpenContext> open() {
			return getRuleContexts(OpenContext.class);
		}
		public OpenContext open(int i) {
			return getRuleContext(OpenContext.class,i);
		}
		public List<ParagraphContext> paragraph() {
			return getRuleContexts(ParagraphContext.class);
		}
		public ParagraphContext paragraph(int i) {
			return getRuleContext(ParagraphContext.class,i);
		}
		public SpecificationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_specification; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSpecification(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSpecification(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSpecification(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SpecificationContext specification() throws RecognitionException {
		SpecificationContext _localctx = new SpecificationContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_specification);
		int _la;
		try {
			int _alt;
			setState(170);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(150);
					module();
					}
					break;
				}
				setState(156);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(153);
						open();
						}
						} 
					}
					setState(158);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
				}
				setState(160);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(159);
					module();
					}
				}

				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__14) | (1L << T__16) | (1L << T__17) | (1L << T__20) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32))) != 0) || _la==T__78 || _la==ID) {
					{
					{
					setState(162);
					paragraph();
					}
					}
					setState(167);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(168);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModuleContext extends ParserRuleContext {
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public ModuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleContext module() throws RecognitionException {
		ModuleContext _localctx = new ModuleContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_module);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(T__0);
			setState(173);
			name();
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(174);
				match(T__1);
				setState(175);
				name();
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(176);
					match(T__2);
					setState(178);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==T__3) {
						{
						setState(177);
						match(T__3);
						}
					}

					setState(180);
					name();
					}
					}
					setState(185);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(186);
				match(T__4);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Names_optContext extends ParserRuleContext {
		public NamesContext names() {
			return getRuleContext(NamesContext.class,0);
		}
		public Names_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_names_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNames_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNames_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNames_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Names_optContext names_opt() throws RecognitionException {
		Names_optContext _localctx = new Names_optContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_names_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__78 || _la==ID) {
				{
				setState(190);
				names();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class As_name_optContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public As_name_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_as_name_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterAs_name_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitAs_name_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitAs_name_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final As_name_optContext as_name_opt() throws RecognitionException {
		As_name_optContext _localctx = new As_name_optContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_as_name_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(193);
				match(T__5);
				setState(194);
				name();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Para_openContext extends ParserRuleContext {
		public Names_optContext names_opt() {
			return getRuleContext(Names_optContext.class,0);
		}
		public Para_openContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_para_open; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterPara_open(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitPara_open(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitPara_open(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Para_openContext para_open() throws RecognitionException {
		Para_openContext _localctx = new Para_openContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_para_open);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(197);
				match(T__1);
				setState(198);
				names_opt();
				setState(199);
				match(T__4);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpenContext extends ParserRuleContext {
		public PrivContext priv() {
			return getRuleContext(PrivContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public Para_openContext para_open() {
			return getRuleContext(Para_openContext.class,0);
		}
		public As_name_optContext as_name_opt() {
			return getRuleContext(As_name_optContext.class,0);
		}
		public OpenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_open; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterOpen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitOpen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitOpen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpenContext open() throws RecognitionException {
		OpenContext _localctx = new OpenContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_open);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			priv();
			setState(204);
			match(T__6);
			setState(205);
			name();
			setState(206);
			para_open();
			setState(207);
			as_name_opt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Macro_exprContext extends ParserRuleContext {
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public Macro_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterMacro_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitMacro_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitMacro_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Macro_exprContext macro_expr() throws RecognitionException {
		Macro_exprContext _localctx = new Macro_exprContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_macro_expr);
		int _la;
		try {
			setState(228);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__7) {
					{
					setState(209);
					match(T__7);
					}
				}

				setState(212);
				match(T__8);
				setState(220);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
				case 1:
					{
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__10) | (1L << T__18) | (1L << T__24) | (1L << T__25) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__36) | (1L << T__37) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (T__78 - 79)) | (1L << (DIGIT - 79)) | (1L << (ID - 79)) | (1L << (STRING - 79)))) != 0)) {
						{
						{
						setState(213);
						expr(0);
						}
						}
						setState(218);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
					break;
				case 2:
					{
					setState(219);
					decls();
					}
					break;
				}
				setState(222);
				match(T__9);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(223);
				match(T__7);
				setState(226);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(224);
					expr(0);
					}
					break;
				case 2:
					{
					setState(225);
					decls();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MacroContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public Macro_exprContext macro_expr() {
			return getRuleContext(Macro_exprContext.class,0);
		}
		public NamesContext names() {
			return getRuleContext(NamesContext.class,0);
		}
		public MacroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_macro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterMacro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitMacro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitMacro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MacroContext macro() throws RecognitionException {
		MacroContext _localctx = new MacroContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_macro);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(T__10);
			setState(231);
			name();
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(232);
				match(T__1);
				setState(233);
				names();
				setState(234);
				match(T__4);
				}
			}

			setState(238);
			macro_expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParagraphContext extends ParserRuleContext {
		public FactDeclContext factDecl() {
			return getRuleContext(FactDeclContext.class,0);
		}
		public AssertDeclContext assertDecl() {
			return getRuleContext(AssertDeclContext.class,0);
		}
		public FunDeclContext funDecl() {
			return getRuleContext(FunDeclContext.class,0);
		}
		public CmdDeclContext cmdDecl() {
			return getRuleContext(CmdDeclContext.class,0);
		}
		public EnumDeclContext enumDecl() {
			return getRuleContext(EnumDeclContext.class,0);
		}
		public SigDeclContext sigDecl() {
			return getRuleContext(SigDeclContext.class,0);
		}
		public PredDeclContext predDecl() {
			return getRuleContext(PredDeclContext.class,0);
		}
		public MacroContext macro() {
			return getRuleContext(MacroContext.class,0);
		}
		public ParagraphContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paragraph; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterParagraph(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitParagraph(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitParagraph(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParagraphContext paragraph() throws RecognitionException {
		ParagraphContext _localctx = new ParagraphContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_paragraph);
		try {
			setState(248);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				factDecl();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(241);
				assertDecl();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(242);
				funDecl();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(243);
				cmdDecl();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(244);
				enumDecl();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(245);
				sigDecl();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(246);
				predDecl();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(247);
				macro();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FactDeclContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public FactDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterFactDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitFactDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitFactDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactDeclContext factDecl() throws RecognitionException {
		FactDeclContext _localctx = new FactDeclContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_factDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250);
			match(T__11);
			setState(252);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__78 || _la==ID) {
				{
				setState(251);
				name();
				}
			}

			setState(254);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Name_optContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public Name_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterName_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitName_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitName_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Name_optContext name_opt() throws RecognitionException {
		Name_optContext _localctx = new Name_optContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_name_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(257);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__78 || _la==ID) {
				{
				setState(256);
				name();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssertDeclContext extends ParserRuleContext {
		public Name_optContext name_opt() {
			return getRuleContext(Name_optContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public AssertDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assertDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterAssertDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitAssertDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitAssertDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssertDeclContext assertDecl() throws RecognitionException {
		AssertDeclContext _localctx = new AssertDeclContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_assertDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(T__12);
			setState(260);
			name_opt();
			setState(261);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameIDContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode ID() { return getToken(ALLOYParser.ID, 0); }
		public NameIDContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameID; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNameID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNameID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNameID(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameIDContext nameID() throws RecognitionException {
		NameIDContext _localctx = new NameIDContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_nameID);
		try {
			setState(268);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(263);
				name();
				setState(264);
				match(T__13);
				setState(265);
				match(ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(267);
				match(ID);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Decls_fContext extends ParserRuleContext {
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public Decls_fContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls_f; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDecls_f(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDecls_f(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDecls_f(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Decls_fContext decls_f() throws RecognitionException {
		Decls_fContext _localctx = new Decls_fContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_decls_f);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			match(T__8);
			setState(271);
			decls();
			setState(272);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunExprContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FunExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterFunExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitFunExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitFunExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunExprContext funExpr() throws RecognitionException {
		FunExprContext _localctx = new FunExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_funExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunDeclContext extends ParserRuleContext {
		public PrivContext priv() {
			return getRuleContext(PrivContext.class,0);
		}
		public NameIDContext nameID() {
			return getRuleContext(NameIDContext.class,0);
		}
		public ParaDecls_optContext paraDecls_opt() {
			return getRuleContext(ParaDecls_optContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public FunExprContext funExpr() {
			return getRuleContext(FunExprContext.class,0);
		}
		public FunDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterFunDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitFunDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitFunDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunDeclContext funDecl() throws RecognitionException {
		FunDeclContext _localctx = new FunDeclContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_funDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(276);
			priv();
			setState(277);
			match(T__14);
			setState(278);
			nameID();
			setState(279);
			paraDecls_opt();
			setState(280);
			match(T__15);
			setState(281);
			expr(0);
			setState(282);
			match(T__8);
			setState(283);
			funExpr();
			setState(284);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameOrBlockContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public NameIDContext nameID() {
			return getRuleContext(NameIDContext.class,0);
		}
		public NameOrBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameOrBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNameOrBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNameOrBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNameOrBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameOrBlockContext nameOrBlock() throws RecognitionException {
		NameOrBlockContext _localctx = new NameOrBlockContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_nameOrBlock);
		try {
			setState(295);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(288);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(286);
					name();
					}
					break;
				case 2:
					{
					setState(287);
					block();
					}
					break;
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(293);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
				case 1:
					{
					setState(290);
					nameID();
					setState(291);
					block();
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Name_cmd_optContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public Name_cmd_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name_cmd_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterName_cmd_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitName_cmd_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitName_cmd_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Name_cmd_optContext name_cmd_opt() throws RecognitionException {
		Name_cmd_optContext _localctx = new Name_cmd_optContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_name_cmd_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(300);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__78 || _la==ID) {
				{
				setState(297);
				name();
				setState(298);
				match(T__15);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Scope_optContext extends ParserRuleContext {
		public ScopeContext scope() {
			return getRuleContext(ScopeContext.class,0);
		}
		public Scope_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scope_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterScope_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitScope_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitScope_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Scope_optContext scope_opt() throws RecognitionException {
		Scope_optContext _localctx = new Scope_optContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_scope_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(303);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22 || _la==T__23) {
				{
				setState(302);
				scope();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Run_or_checkContext extends ParserRuleContext {
		public Run_or_checkContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_run_or_check; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterRun_or_check(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitRun_or_check(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitRun_or_check(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Run_or_checkContext run_or_check() throws RecognitionException {
		Run_or_checkContext _localctx = new Run_or_checkContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_run_or_check);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			_la = _input.LA(1);
			if ( !(_la==T__16 || _la==T__17) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CmdDeclContext extends ParserRuleContext {
		public Name_cmd_optContext name_cmd_opt() {
			return getRuleContext(Name_cmd_optContext.class,0);
		}
		public Run_or_checkContext run_or_check() {
			return getRuleContext(Run_or_checkContext.class,0);
		}
		public NameOrBlockContext nameOrBlock() {
			return getRuleContext(NameOrBlockContext.class,0);
		}
		public Scope_optContext scope_opt() {
			return getRuleContext(Scope_optContext.class,0);
		}
		public CmdDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmdDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterCmdDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitCmdDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitCmdDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CmdDeclContext cmdDecl() throws RecognitionException {
		CmdDeclContext _localctx = new CmdDeclContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_cmdDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307);
			name_cmd_opt();
			setState(308);
			run_or_check();
			setState(309);
			nameOrBlock();
			setState(310);
			scope_opt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParaDeclsContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public ParaDeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paraDecls; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterParaDecls(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitParaDecls(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitParaDecls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaDeclsContext paraDecls() throws RecognitionException {
		ParaDeclsContext _localctx = new ParaDeclsContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_paraDecls);
		int _la;
		try {
			setState(336);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__18:
				enterOuterAlt(_localctx, 1);
				{
				setState(312);
				match(T__18);
				setState(321);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 28)) & ~0x3f) == 0 && ((1L << (_la - 28)) & ((1L << (T__27 - 28)) | (1L << (T__48 - 28)) | (1L << (T__49 - 28)) | (1L << (T__78 - 28)) | (1L << (ID - 28)))) != 0)) {
					{
					setState(313);
					decl();
					setState(318);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(314);
						match(T__2);
						setState(315);
						decl();
						}
						}
						setState(320);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(323);
				match(T__19);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(324);
				match(T__1);
				setState(333);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (((((_la - 28)) & ~0x3f) == 0 && ((1L << (_la - 28)) & ((1L << (T__27 - 28)) | (1L << (T__48 - 28)) | (1L << (T__49 - 28)) | (1L << (T__78 - 28)) | (1L << (ID - 28)))) != 0)) {
					{
					setState(325);
					decl();
					setState(330);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(326);
						match(T__2);
						setState(327);
						decl();
						}
						}
						setState(332);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(335);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParaDecls_optContext extends ParserRuleContext {
		public ParaDeclsContext paraDecls() {
			return getRuleContext(ParaDeclsContext.class,0);
		}
		public ParaDecls_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paraDecls_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterParaDecls_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitParaDecls_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitParaDecls_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaDecls_optContext paraDecls_opt() throws RecognitionException {
		ParaDecls_optContext _localctx = new ParaDecls_optContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_paraDecls_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(339);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1 || _la==T__18) {
				{
				setState(338);
				paraDecls();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PredDeclContext extends ParserRuleContext {
		public NameIDContext nameID() {
			return getRuleContext(NameIDContext.class,0);
		}
		public ParaDecls_optContext paraDecls_opt() {
			return getRuleContext(ParaDecls_optContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public PredDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterPredDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitPredDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitPredDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredDeclContext predDecl() throws RecognitionException {
		PredDeclContext _localctx = new PredDeclContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_predDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(341);
			match(T__20);
			setState(342);
			nameID();
			setState(343);
			paraDecls_opt();
			setState(344);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypescopesContext extends ParserRuleContext {
		public List<TypescopeContext> typescope() {
			return getRuleContexts(TypescopeContext.class);
		}
		public TypescopeContext typescope(int i) {
			return getRuleContext(TypescopeContext.class,i);
		}
		public TypescopesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typescopes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterTypescopes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitTypescopes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitTypescopes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypescopesContext typescopes() throws RecognitionException {
		TypescopesContext _localctx = new TypescopesContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_typescopes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			typescope();
			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(347);
				match(T__2);
				setState(348);
				typescope();
				}
				}
				setState(353);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class But_typescopesContext extends ParserRuleContext {
		public TypescopesContext typescopes() {
			return getRuleContext(TypescopesContext.class,0);
		}
		public But_typescopesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_but_typescopes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBut_typescopes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBut_typescopes(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBut_typescopes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final But_typescopesContext but_typescopes() throws RecognitionException {
		But_typescopesContext _localctx = new But_typescopesContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_but_typescopes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__21) {
				{
				setState(354);
				match(T__21);
				setState(355);
				typescopes();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Expect_digitContext extends ParserRuleContext {
		public TerminalNode DIGIT() { return getToken(ALLOYParser.DIGIT, 0); }
		public Expect_digitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expect_digit; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterExpect_digit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitExpect_digit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitExpect_digit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Expect_digitContext expect_digit() throws RecognitionException {
		Expect_digitContext _localctx = new Expect_digitContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_expect_digit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__22) {
				{
				setState(358);
				match(T__22);
				setState(359);
				match(DIGIT);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ScopeContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public But_typescopesContext but_typescopes() {
			return getRuleContext(But_typescopesContext.class,0);
		}
		public Expect_digitContext expect_digit() {
			return getRuleContext(Expect_digitContext.class,0);
		}
		public TypescopesContext typescopes() {
			return getRuleContext(TypescopesContext.class,0);
		}
		public TerminalNode DIGIT() { return getToken(ALLOYParser.DIGIT, 0); }
		public ScopeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scope; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterScope(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitScope(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitScope(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScopeContext scope() throws RecognitionException {
		ScopeContext _localctx = new ScopeContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_scope);
		try {
			setState(373);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(362);
				match(T__23);
				setState(363);
				number();
				setState(364);
				but_typescopes();
				setState(365);
				expect_digit();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(367);
				match(T__23);
				setState(368);
				typescopes();
				setState(369);
				expect_digit();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(371);
				match(T__22);
				setState(372);
				match(DIGIT);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Exactly_optContext extends ParserRuleContext {
		public Exactly_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exactly_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterExactly_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitExactly_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitExactly_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exactly_optContext exactly_opt() throws RecognitionException {
		Exactly_optContext _localctx = new Exactly_optContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_exactly_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(375);
				match(T__3);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypescopeContext extends ParserRuleContext {
		public Exactly_optContext exactly_opt() {
			return getRuleContext(Exactly_optContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TypescopeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typescope; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterTypescope(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitTypescope(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitTypescope(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypescopeContext typescope() throws RecognitionException {
		TypescopeContext _localctx = new TypescopeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_typescope);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(378);
			exactly_opt();
			setState(379);
			number();
			setState(383);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__78:
			case ID:
				{
				setState(380);
				name();
				}
				break;
			case T__24:
				{
				setState(381);
				match(T__24);
				}
				break;
			case T__25:
				{
				setState(382);
				match(T__25);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclsContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDecls(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDecls(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDecls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_decls);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(393);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				{
				setState(385);
				decl();
				setState(390);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(386);
					match(T__2);
					setState(387);
					decl();
					}
					}
					setState(392);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicityContext extends ParserRuleContext {
		public MultContext mult() {
			return getRuleContext(MultContext.class,0);
		}
		public MultiplicityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterMultiplicity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitMultiplicity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitMultiplicity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicityContext multiplicity() throws RecognitionException {
		MultiplicityContext _localctx = new MultiplicityContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_multiplicity);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32))) != 0)) {
				{
				setState(395);
				mult();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AbsContext extends ParserRuleContext {
		public AbsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterAbs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitAbs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitAbs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AbsContext abs() throws RecognitionException {
		AbsContext _localctx = new AbsContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_abs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(399);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__26) {
				{
				setState(398);
				match(T__26);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrivContext extends ParserRuleContext {
		public PrivContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_priv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterPriv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitPriv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitPriv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrivContext priv() throws RecognitionException {
		PrivContext _localctx = new PrivContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_priv);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__27) {
				{
				setState(401);
				match(T__27);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Abs_multiplicityContext extends ParserRuleContext {
		public AbsContext abs() {
			return getRuleContext(AbsContext.class,0);
		}
		public MultiplicityContext multiplicity() {
			return getRuleContext(MultiplicityContext.class,0);
		}
		public Abs_multiplicityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_abs_multiplicity; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterAbs_multiplicity(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitAbs_multiplicity(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitAbs_multiplicity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Abs_multiplicityContext abs_multiplicity() throws RecognitionException {
		Abs_multiplicityContext _localctx = new Abs_multiplicityContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_abs_multiplicity);
		try {
			setState(410);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,43,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(404);
				abs();
				setState(405);
				multiplicity();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(407);
				multiplicity();
				setState(408);
				abs();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SigDeclContext extends ParserRuleContext {
		public PrivContext priv() {
			return getRuleContext(PrivContext.class,0);
		}
		public Abs_multiplicityContext abs_multiplicity() {
			return getRuleContext(Abs_multiplicityContext.class,0);
		}
		public NamesContext names() {
			return getRuleContext(NamesContext.class,0);
		}
		public SigExtensionContext sigExtension() {
			return getRuleContext(SigExtensionContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public Block_optContext block_opt() {
			return getRuleContext(Block_optContext.class,0);
		}
		public SigDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sigDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSigDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSigDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSigDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SigDeclContext sigDecl() throws RecognitionException {
		SigDeclContext _localctx = new SigDeclContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_sigDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			priv();
			setState(413);
			abs_multiplicity();
			setState(414);
			match(T__28);
			setState(415);
			names();
			setState(416);
			sigExtension();
			setState(417);
			match(T__8);
			setState(418);
			decls();
			setState(419);
			match(T__9);
			setState(420);
			block_opt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NamesContext extends ParserRuleContext {
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public NamesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_names; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNames(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNames(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNames(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NamesContext names() throws RecognitionException {
		NamesContext _localctx = new NamesContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_names);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			name();
			setState(427);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(423);
				match(T__2);
				setState(424);
				name();
				}
				}
				setState(429);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnumDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NamesContext names() {
			return getRuleContext(NamesContext.class,0);
		}
		public EnumDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enumDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterEnumDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitEnumDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitEnumDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnumDeclContext enumDecl() throws RecognitionException {
		EnumDeclContext _localctx = new EnumDeclContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_enumDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430);
			match(T__29);
			setState(431);
			name();
			setState(432);
			match(T__8);
			setState(433);
			names();
			setState(434);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultContext extends ParserRuleContext {
		public MultContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mult; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitMult(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitMult(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultContext mult() throws RecognitionException {
		MultContext _localctx = new MultContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_mult);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(436);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnionContext extends ParserRuleContext {
		public List<NameContext> name() {
			return getRuleContexts(NameContext.class);
		}
		public NameContext name(int i) {
			return getRuleContext(NameContext.class,i);
		}
		public UnionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_union; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterUnion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitUnion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitUnion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnionContext union() throws RecognitionException {
		UnionContext _localctx = new UnionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_union);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(438);
			name();
			setState(443);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(439);
					match(T__33);
					setState(440);
					name();
					}
					} 
				}
				setState(445);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,45,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SuperSetContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public UnionContext union() {
			return getRuleContext(UnionContext.class,0);
		}
		public SuperSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_superSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSuperSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSuperSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSuperSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuperSetContext superSet() throws RecognitionException {
		SuperSetContext _localctx = new SuperSetContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_superSet);
		try {
			setState(448);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,46,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(446);
				name();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(447);
				union();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SigExtContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public SuperSetContext superSet() {
			return getRuleContext(SuperSetContext.class,0);
		}
		public SigExtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sigExt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSigExt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSigExt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSigExt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SigExtContext sigExt() throws RecognitionException {
		SigExtContext _localctx = new SigExtContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_sigExt);
		try {
			setState(454);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__34:
				enterOuterAlt(_localctx, 1);
				{
				setState(450);
				match(T__34);
				setState(451);
				name();
				}
				break;
			case T__35:
				enterOuterAlt(_localctx, 2);
				{
				setState(452);
				match(T__35);
				setState(453);
				superSet();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SigExtensionContext extends ParserRuleContext {
		public SigExtContext sigExt() {
			return getRuleContext(SigExtContext.class,0);
		}
		public SigExtensionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sigExtension; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSigExtension(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSigExtension(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSigExtension(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SigExtensionContext sigExtension() throws RecognitionException {
		SigExtensionContext _localctx = new SigExtensionContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_sigExtension);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(457);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__34 || _la==T__35) {
				{
				setState(456);
				sigExt();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprsContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ExprsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterExprs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitExprs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitExprs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprsContext exprs() throws RecognitionException {
		ExprsContext _localctx = new ExprsContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_exprs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(467);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__10) | (1L << T__18) | (1L << T__24) | (1L << T__25) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__36) | (1L << T__37) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (T__78 - 79)) | (1L << (DIGIT - 79)) | (1L << (ID - 79)) | (1L << (STRING - 79)))) != 0)) {
				{
				setState(459);
				expr(0);
				setState(464);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(460);
					match(T__2);
					setState(461);
					expr(0);
					}
					}
					setState(466);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotOpContext extends ParserRuleContext {
		public NotOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNotOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNotOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNotOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotOpContext notOp() throws RecognitionException {
		NotOpContext _localctx = new NotOpContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_notOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(470);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__36 || _la==T__37) {
				{
				setState(469);
				_la = _input.LA(1);
				if ( !(_la==T__36 || _la==T__37) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Decls_eContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public Decls_eContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls_e; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDecls_e(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDecls_e(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDecls_e(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Decls_eContext decls_e() throws RecognitionException {
		Decls_eContext _localctx = new Decls_eContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_decls_e);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(472);
			decl();
			setState(477);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(473);
				match(T__2);
				setState(474);
				decl();
				}
				}
				setState(479);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public List<LetDeclContext> letDecl() {
			return getRuleContexts(LetDeclContext.class);
		}
		public LetDeclContext letDecl(int i) {
			return getRuleContext(LetDeclContext.class,i);
		}
		public BlockOrBarContext blockOrBar() {
			return getRuleContext(BlockOrBarContext.class,0);
		}
		public QuantContext quant() {
			return getRuleContext(QuantContext.class,0);
		}
		public Decls_eContext decls_e() {
			return getRuleContext(Decls_eContext.class,0);
		}
		public UnOpContext unOp() {
			return getRuleContext(UnOpContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DisjFuncContext disjFunc() {
			return getRuleContext(DisjFuncContext.class,0);
		}
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BinOpContext binOp() {
			return getRuleContext(BinOpContext.class,0);
		}
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public Decls_fContext decls_f() {
			return getRuleContext(Decls_fContext.class,0);
		}
		public TerminalNode STRING() { return getToken(ALLOYParser.STRING, 0); }
		public ArrowOpContext arrowOp() {
			return getRuleContext(ArrowOpContext.class,0);
		}
		public NotOpContext notOp() {
			return getRuleContext(NotOpContext.class,0);
		}
		public CompareOpContext compareOp() {
			return getRuleContext(CompareOpContext.class,0);
		}
		public ExprsContext exprs() {
			return getRuleContext(ExprsContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 92;
		enterRecursionRule(_localctx, 92, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(531);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,55,_ctx) ) {
			case 1:
				{
				setState(481);
				match(T__10);
				setState(482);
				letDecl();
				setState(487);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(483);
					match(T__2);
					setState(484);
					letDecl();
					}
					}
					setState(489);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(490);
				blockOrBar();
				}
				break;
			case 2:
				{
				setState(492);
				quant();
				setState(493);
				decls_e();
				setState(494);
				blockOrBar();
				}
				break;
			case 3:
				{
				setState(496);
				unOp();
				setState(497);
				expr(19);
				}
				break;
			case 4:
				{
				setState(499);
				disjFunc();
				}
				break;
			case 5:
				{
				setState(500);
				constant();
				}
				break;
			case 6:
				{
				setState(501);
				match(T__25);
				}
				break;
			case 7:
				{
				setState(502);
				match(T__41);
				}
				break;
			case 8:
				{
				setState(503);
				match(T__18);
				setState(504);
				expr(0);
				setState(505);
				match(T__19);
				}
				break;
			case 9:
				{
				setState(507);
				name();
				}
				break;
			case 10:
				{
				setState(508);
				match(T__42);
				setState(509);
				name();
				}
				break;
			case 11:
				{
				setState(510);
				match(T__43);
				}
				break;
			case 12:
				{
				setState(511);
				block();
				}
				break;
			case 13:
				{
				setState(512);
				quant();
				setState(513);
				expr(0);
				setState(514);
				binOp();
				setState(515);
				expr(4);
				}
				break;
			case 14:
				{
				setState(517);
				match(T__8);
				setState(518);
				decl();
				setState(523);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(519);
					match(T__2);
					setState(520);
					decl();
					}
					}
					setState(525);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(526);
				blockOrBar();
				setState(527);
				match(T__9);
				}
				break;
			case 15:
				{
				setState(529);
				decls_f();
				}
				break;
			case 16:
				{
				setState(530);
				match(STRING);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(559);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(557);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,56,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(533);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(534);
						binOp();
						setState(535);
						expr(19);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(537);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(538);
						arrowOp();
						setState(539);
						expr(18);
						}
						break;
					case 3:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(541);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(542);
						notOp();
						setState(543);
						compareOp();
						setState(544);
						expr(17);
						}
						break;
					case 4:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(546);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(547);
						_la = _input.LA(1);
						if ( !(_la==T__38 || _la==T__39) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(548);
						expr(0);
						setState(549);
						match(T__40);
						setState(550);
						expr(16);
						}
						break;
					case 5:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(552);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(553);
						match(T__1);
						setState(554);
						exprs();
						setState(555);
						match(T__4);
						}
						break;
					}
					} 
				}
				setState(561);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,57,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class NumContext extends ParserRuleContext {
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public NumContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_num; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNum(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNum(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNum(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumContext num() throws RecognitionException {
		NumContext _localctx = new NumContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_num);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(563);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__44) {
				{
				setState(562);
				match(T__44);
				}
			}

			setState(565);
			number();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Const_setsContext extends ParserRuleContext {
		public Const_setsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_const_sets; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterConst_sets(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitConst_sets(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitConst_sets(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Const_setsContext const_sets() throws RecognitionException {
		Const_setsContext _localctx = new Const_setsContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_const_sets);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(567);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__45) | (1L << T__46) | (1L << T__47))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public NumContext num() {
			return getRuleContext(NumContext.class,0);
		}
		public Const_setsContext const_sets() {
			return getRuleContext(Const_setsContext.class,0);
		}
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_constant);
		try {
			setState(571);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__44:
			case DIGIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(569);
				num();
				}
				break;
			case T__45:
			case T__46:
			case T__47:
				enterOuterAlt(_localctx, 2);
				{
				setState(570);
				const_sets();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjFuncContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DisjFuncContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjFunc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDisjFunc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDisjFunc(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDisjFunc(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DisjFuncContext disjFunc() throws RecognitionException {
		DisjFuncContext _localctx = new DisjFuncContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_disjFunc);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(573);
			match(T__48);
			setState(574);
			match(T__1);
			setState(583);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__10) | (1L << T__18) | (1L << T__24) | (1L << T__25) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__36) | (1L << T__37) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (T__78 - 79)) | (1L << (DIGIT - 79)) | (1L << (ID - 79)) | (1L << (STRING - 79)))) != 0)) {
				{
				setState(575);
				expr(0);
				setState(580);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(576);
					match(T__2);
					setState(577);
					expr(0);
					}
					}
					setState(582);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(585);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjointContext extends ParserRuleContext {
		public DisjointContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disjoint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDisjoint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDisjoint(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDisjoint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DisjointContext disjoint() throws RecognitionException {
		DisjointContext _localctx = new DisjointContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_disjoint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(588);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__48 || _la==T__49) {
				{
				setState(587);
				_la = _input.LA(1);
				if ( !(_la==T__48 || _la==T__49) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DisjContext extends ParserRuleContext {
		public DisjContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_disj; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDisj(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDisj(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDisj(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DisjContext disj() throws RecognitionException {
		DisjContext _localctx = new DisjContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_disj);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(591);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				{
				setState(590);
				match(T__48);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Comma_optContext extends ParserRuleContext {
		public Comma_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comma_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterComma_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitComma_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitComma_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Comma_optContext comma_opt() throws RecognitionException {
		Comma_optContext _localctx = new Comma_optContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_comma_opt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(594);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,64,_ctx) ) {
			case 1:
				{
				setState(593);
				match(T__2);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public PrivContext priv() {
			return getRuleContext(PrivContext.class,0);
		}
		public DisjointContext disjoint() {
			return getRuleContext(DisjointContext.class,0);
		}
		public NamesContext names() {
			return getRuleContext(NamesContext.class,0);
		}
		public DisjContext disj() {
			return getRuleContext(DisjContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Comma_optContext comma_opt() {
			return getRuleContext(Comma_optContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(596);
			priv();
			setState(597);
			disjoint();
			setState(598);
			names();
			setState(599);
			match(T__15);
			setState(600);
			disj();
			setState(601);
			expr(0);
			setState(602);
			comma_opt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LetDeclContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public LetDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letDecl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterLetDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitLetDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitLetDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LetDeclContext letDecl() throws RecognitionException {
		LetDeclContext _localctx = new LetDeclContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_letDecl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			name();
			setState(605);
			match(T__7);
			setState(606);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuantContext extends ParserRuleContext {
		public QuantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quant; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterQuant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitQuant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitQuant(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantContext quant() throws RecognitionException {
		QuantContext _localctx = new QuantContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_quant);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(608);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__50) | (1L << T__51) | (1L << T__52))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetCardContext extends ParserRuleContext {
		public SetCardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setCard; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSetCard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSetCard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSetCard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetCardContext setCard() throws RecognitionException {
		SetCardContext _localctx = new SetCardContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_setCard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(610);
			match(T__53);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TcOpContext extends ParserRuleContext {
		public TcOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tcOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterTcOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitTcOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitTcOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TcOpContext tcOp() throws RecognitionException {
		TcOpContext _localctx = new TcOpContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_tcOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(612);
			_la = _input.LA(1);
			if ( !(_la==T__54 || _la==T__55) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnOpContext extends ParserRuleContext {
		public MultContext mult() {
			return getRuleContext(MultContext.class,0);
		}
		public SetCardContext setCard() {
			return getRuleContext(SetCardContext.class,0);
		}
		public TcOpContext tcOp() {
			return getRuleContext(TcOpContext.class,0);
		}
		public UnOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterUnOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitUnOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitUnOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnOpContext unOp() throws RecognitionException {
		UnOpContext _localctx = new UnOpContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_unOp);
		try {
			setState(623);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__36:
				enterOuterAlt(_localctx, 1);
				{
				setState(614);
				match(T__36);
				}
				break;
			case T__37:
				enterOuterAlt(_localctx, 2);
				{
				setState(615);
				match(T__37);
				}
				break;
			case T__51:
				enterOuterAlt(_localctx, 3);
				{
				setState(616);
				match(T__51);
				}
				break;
			case T__30:
			case T__31:
			case T__32:
				enterOuterAlt(_localctx, 4);
				{
				setState(617);
				mult();
				}
				break;
			case T__56:
				enterOuterAlt(_localctx, 5);
				{
				setState(618);
				match(T__56);
				}
				break;
			case T__53:
				enterOuterAlt(_localctx, 6);
				{
				setState(619);
				setCard();
				}
				break;
			case T__57:
				enterOuterAlt(_localctx, 7);
				{
				setState(620);
				match(T__57);
				}
				break;
			case T__54:
			case T__55:
				enterOuterAlt(_localctx, 8);
				{
				setState(621);
				tcOp();
				}
				break;
			case T__24:
				enterOuterAlt(_localctx, 9);
				{
				setState(622);
				match(T__24);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Bit_shifter_operatorsContext extends ParserRuleContext {
		public Bit_shifter_operatorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bit_shifter_operators; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBit_shifter_operators(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBit_shifter_operators(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBit_shifter_operators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Bit_shifter_operatorsContext bit_shifter_operators() throws RecognitionException {
		Bit_shifter_operatorsContext _localctx = new Bit_shifter_operatorsContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_bit_shifter_operators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(625);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__58) | (1L << T__59) | (1L << T__60))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DotOpContext extends ParserRuleContext {
		public DotOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dotOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterDotOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitDotOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitDotOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DotOpContext dotOp() throws RecognitionException {
		DotOpContext _localctx = new DotOpContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_dotOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(627);
			match(T__13);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AddContext extends ParserRuleContext {
		public AddContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_add; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitAdd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddContext add() throws RecognitionException {
		AddContext _localctx = new AddContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_add);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(629);
			match(T__33);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SubContext extends ParserRuleContext {
		public SubContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SubContext sub() throws RecognitionException {
		SubContext _localctx = new SubContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_sub);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(631);
			match(T__44);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BinOpContext extends ParserRuleContext {
		public AddContext add() {
			return getRuleContext(AddContext.class,0);
		}
		public SubContext sub() {
			return getRuleContext(SubContext.class,0);
		}
		public DotOpContext dotOp() {
			return getRuleContext(DotOpContext.class,0);
		}
		public Bit_shifter_operatorsContext bit_shifter_operators() {
			return getRuleContext(Bit_shifter_operatorsContext.class,0);
		}
		public BinOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBinOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBinOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBinOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BinOpContext binOp() throws RecognitionException {
		BinOpContext _localctx = new BinOpContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_binOp);
		try {
			setState(649);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__61:
				enterOuterAlt(_localctx, 1);
				{
				setState(633);
				match(T__61);
				}
				break;
			case T__62:
				enterOuterAlt(_localctx, 2);
				{
				setState(634);
				match(T__62);
				}
				break;
			case T__63:
				enterOuterAlt(_localctx, 3);
				{
				setState(635);
				match(T__63);
				}
				break;
			case T__64:
				enterOuterAlt(_localctx, 4);
				{
				setState(636);
				match(T__64);
				}
				break;
			case T__65:
				enterOuterAlt(_localctx, 5);
				{
				setState(637);
				match(T__65);
				}
				break;
			case T__66:
				enterOuterAlt(_localctx, 6);
				{
				setState(638);
				match(T__66);
				}
				break;
			case T__38:
				enterOuterAlt(_localctx, 7);
				{
				setState(639);
				match(T__38);
				}
				break;
			case T__39:
				enterOuterAlt(_localctx, 8);
				{
				setState(640);
				match(T__39);
				}
				break;
			case T__67:
				enterOuterAlt(_localctx, 9);
				{
				setState(641);
				match(T__67);
				}
				break;
			case T__33:
				enterOuterAlt(_localctx, 10);
				{
				setState(642);
				add();
				}
				break;
			case T__44:
				enterOuterAlt(_localctx, 11);
				{
				setState(643);
				sub();
				}
				break;
			case T__68:
				enterOuterAlt(_localctx, 12);
				{
				setState(644);
				match(T__68);
				}
				break;
			case T__69:
				enterOuterAlt(_localctx, 13);
				{
				setState(645);
				match(T__69);
				}
				break;
			case T__70:
				enterOuterAlt(_localctx, 14);
				{
				setState(646);
				match(T__70);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 15);
				{
				setState(647);
				dotOp();
				}
				break;
			case T__58:
			case T__59:
			case T__60:
				enterOuterAlt(_localctx, 16);
				{
				setState(648);
				bit_shifter_operators();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Mult_or_setContext extends ParserRuleContext {
		public MultContext mult() {
			return getRuleContext(MultContext.class,0);
		}
		public Mult_or_setContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mult_or_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterMult_or_set(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitMult_or_set(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitMult_or_set(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Mult_or_setContext mult_or_set() throws RecognitionException {
		Mult_or_setContext _localctx = new Mult_or_setContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_mult_or_set);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(653);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				{
				setState(651);
				mult();
				}
				break;
			case 2:
				{
				setState(652);
				match(T__56);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrowOpContext extends ParserRuleContext {
		public List<Mult_or_setContext> mult_or_set() {
			return getRuleContexts(Mult_or_setContext.class);
		}
		public Mult_or_setContext mult_or_set(int i) {
			return getRuleContext(Mult_or_setContext.class,i);
		}
		public ArrowOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrowOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterArrowOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitArrowOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitArrowOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrowOpContext arrowOp() throws RecognitionException {
		ArrowOpContext _localctx = new ArrowOpContext(_ctx, getState());
		enterRule(_localctx, 132, RULE_arrowOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(655);
			mult_or_set();
			setState(656);
			match(T__71);
			setState(657);
			mult_or_set();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Rel_operatorsContext extends ParserRuleContext {
		public Rel_operatorsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rel_operators; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterRel_operators(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitRel_operators(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitRel_operators(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Rel_operatorsContext rel_operators() throws RecognitionException {
		Rel_operatorsContext _localctx = new Rel_operatorsContext(_ctx, getState());
		enterRule(_localctx, 134, RULE_rel_operators);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(659);
			_la = _input.LA(1);
			if ( !(_la==T__7 || ((((_la - 73)) & ~0x3f) == 0 && ((1L << (_la - 73)) & ((1L << (T__72 - 73)) | (1L << (T__73 - 73)) | (1L << (T__74 - 73)) | (1L << (T__75 - 73)) | (1L << (T__76 - 73)))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompareOpContext extends ParserRuleContext {
		public Rel_operatorsContext rel_operators() {
			return getRuleContext(Rel_operatorsContext.class,0);
		}
		public CompareOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compareOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterCompareOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitCompareOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitCompareOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompareOpContext compareOp() throws RecognitionException {
		CompareOpContext _localctx = new CompareOpContext(_ctx, getState());
		enterRule(_localctx, 136, RULE_compareOp);
		try {
			setState(663);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__72:
			case T__73:
			case T__74:
			case T__75:
			case T__76:
				enterOuterAlt(_localctx, 1);
				{
				setState(661);
				rel_operators();
				}
				break;
			case T__35:
				enterOuterAlt(_localctx, 2);
				{
				setState(662);
				match(T__35);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 138, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(665);
			match(T__8);
			setState(669);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__8) | (1L << T__10) | (1L << T__18) | (1L << T__24) | (1L << T__25) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__36) | (1L << T__37) | (1L << T__41) | (1L << T__42) | (1L << T__43) | (1L << T__44) | (1L << T__45) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << T__53) | (1L << T__54) | (1L << T__55) | (1L << T__56) | (1L << T__57))) != 0) || ((((_la - 79)) & ~0x3f) == 0 && ((1L << (_la - 79)) & ((1L << (T__78 - 79)) | (1L << (DIGIT - 79)) | (1L << (ID - 79)) | (1L << (STRING - 79)))) != 0)) {
				{
				{
				setState(666);
				expr(0);
				}
				}
				setState(671);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(672);
			match(T__9);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Block_optContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public Block_optContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_opt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBlock_opt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBlock_opt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBlock_opt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Block_optContext block_opt() throws RecognitionException {
		Block_optContext _localctx = new Block_optContext(_ctx, getState());
		enterRule(_localctx, 140, RULE_block_opt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(675);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(674);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockOrBarContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BarContext bar() {
			return getRuleContext(BarContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BlockOrBarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockOrBar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBlockOrBar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBlockOrBar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBlockOrBar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockOrBarContext blockOrBar() throws RecognitionException {
		BlockOrBarContext _localctx = new BlockOrBarContext(_ctx, getState());
		enterRule(_localctx, 142, RULE_blockOrBar);
		try {
			setState(681);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(677);
				block();
				}
				break;
			case T__77:
				enterOuterAlt(_localctx, 2);
				{
				setState(678);
				bar();
				setState(679);
				expr(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BarContext extends ParserRuleContext {
		public BarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterBar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitBar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitBar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BarContext bar() throws RecognitionException {
		BarContext _localctx = new BarContext(_ctx, getState());
		enterRule(_localctx, 144, RULE_bar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(683);
			match(T__77);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(ALLOYParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(ALLOYParser.ID, i);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 146, RULE_name);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(686);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__78) {
				{
				setState(685);
				match(T__78);
				}
			}

			setState(692);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(688);
					match(ID);
					setState(689);
					match(T__79);
					}
					} 
				}
				setState(694);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,73,_ctx);
			}
			setState(695);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NumberContext extends ParserRuleContext {
		public List<TerminalNode> DIGIT() { return getTokens(ALLOYParser.DIGIT); }
		public TerminalNode DIGIT(int i) {
			return getToken(ALLOYParser.DIGIT, i);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ALLOYListener ) ((ALLOYListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ALLOYVisitor ) return ((ALLOYVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 148, RULE_number);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(698); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(697);
					match(DIGIT);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(700); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,74,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 46:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3Y\u02c1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\3\2\5\2\u009a\n\2\3\2\7\2\u009d\n\2\f\2\16\2\u00a0"+
		"\13\2\3\2\5\2\u00a3\n\2\3\2\7\2\u00a6\n\2\f\2\16\2\u00a9\13\2\3\2\3\2"+
		"\5\2\u00ad\n\2\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00b5\n\3\3\3\7\3\u00b8\n\3"+
		"\f\3\16\3\u00bb\13\3\3\3\3\3\5\3\u00bf\n\3\3\4\5\4\u00c2\n\4\3\5\3\5\5"+
		"\5\u00c6\n\5\3\6\3\6\3\6\3\6\5\6\u00cc\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b"+
		"\5\b\u00d5\n\b\3\b\3\b\7\b\u00d9\n\b\f\b\16\b\u00dc\13\b\3\b\5\b\u00df"+
		"\n\b\3\b\3\b\3\b\3\b\5\b\u00e5\n\b\5\b\u00e7\n\b\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\5\t\u00ef\n\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u00fb\n\n"+
		"\3\13\3\13\5\13\u00ff\n\13\3\13\3\13\3\f\5\f\u0104\n\f\3\r\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\3\16\5\16\u010f\n\16\3\17\3\17\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\5\22\u0123"+
		"\n\22\3\22\3\22\3\22\5\22\u0128\n\22\5\22\u012a\n\22\3\23\3\23\3\23\5"+
		"\23\u012f\n\23\3\24\5\24\u0132\n\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26"+
		"\3\27\3\27\3\27\3\27\7\27\u013f\n\27\f\27\16\27\u0142\13\27\5\27\u0144"+
		"\n\27\3\27\3\27\3\27\3\27\3\27\7\27\u014b\n\27\f\27\16\27\u014e\13\27"+
		"\5\27\u0150\n\27\3\27\5\27\u0153\n\27\3\30\5\30\u0156\n\30\3\31\3\31\3"+
		"\31\3\31\3\31\3\32\3\32\3\32\7\32\u0160\n\32\f\32\16\32\u0163\13\32\3"+
		"\33\3\33\5\33\u0167\n\33\3\34\3\34\5\34\u016b\n\34\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\35\3\35\3\35\3\35\5\35\u0178\n\35\3\36\5\36\u017b\n"+
		"\36\3\37\3\37\3\37\3\37\3\37\5\37\u0182\n\37\3 \3 \3 \7 \u0187\n \f \16"+
		" \u018a\13 \5 \u018c\n \3!\5!\u018f\n!\3\"\5\"\u0192\n\"\3#\5#\u0195\n"+
		"#\3$\3$\3$\3$\3$\3$\5$\u019d\n$\3%\3%\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3"+
		"&\7&\u01ac\n&\f&\16&\u01af\13&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3)\3)\3)"+
		"\7)\u01bc\n)\f)\16)\u01bf\13)\3*\3*\5*\u01c3\n*\3+\3+\3+\3+\5+\u01c9\n"+
		"+\3,\5,\u01cc\n,\3-\3-\3-\7-\u01d1\n-\f-\16-\u01d4\13-\5-\u01d6\n-\3."+
		"\5.\u01d9\n.\3/\3/\3/\7/\u01de\n/\f/\16/\u01e1\13/\3\60\3\60\3\60\3\60"+
		"\3\60\7\60\u01e8\n\60\f\60\16\60\u01eb\13\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\7\60\u020c"+
		"\n\60\f\60\16\60\u020f\13\60\3\60\3\60\3\60\3\60\3\60\5\60\u0216\n\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\3\60\7\60\u0230\n\60\f\60"+
		"\16\60\u0233\13\60\3\61\5\61\u0236\n\61\3\61\3\61\3\62\3\62\3\63\3\63"+
		"\5\63\u023e\n\63\3\64\3\64\3\64\3\64\3\64\7\64\u0245\n\64\f\64\16\64\u0248"+
		"\13\64\5\64\u024a\n\64\3\64\3\64\3\65\5\65\u024f\n\65\3\66\5\66\u0252"+
		"\n\66\3\67\5\67\u0255\n\67\38\38\38\38\38\38\38\38\39\39\39\39\3:\3:\3"+
		";\3;\3<\3<\3=\3=\3=\3=\3=\3=\3=\3=\3=\5=\u0272\n=\3>\3>\3?\3?\3@\3@\3"+
		"A\3A\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\3B\5B\u028c\nB\3C\3"+
		"C\5C\u0290\nC\3D\3D\3D\3D\3E\3E\3F\3F\5F\u029a\nF\3G\3G\7G\u029e\nG\f"+
		"G\16G\u02a1\13G\3G\3G\3H\5H\u02a6\nH\3I\3I\3I\3I\5I\u02ac\nI\3J\3J\3K"+
		"\5K\u02b1\nK\3K\3K\7K\u02b5\nK\fK\16K\u02b8\13K\3K\3K\3L\6L\u02bd\nL\r"+
		"L\16L\u02be\3L\3\u01bd\3^M\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$"+
		"&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\u0086\u0088\u008a\u008c\u008e\u0090\u0092\u0094\u0096\2\f\3\2\23\24\3"+
		"\2!#\3\2\'(\3\2)*\3\2\60\62\3\2\63\64\4\2!#\65\67\3\29:\3\2=?\4\2\n\n"+
		"KO\2\u02f0\2\u00ac\3\2\2\2\4\u00ae\3\2\2\2\6\u00c1\3\2\2\2\b\u00c5\3\2"+
		"\2\2\n\u00cb\3\2\2\2\f\u00cd\3\2\2\2\16\u00e6\3\2\2\2\20\u00e8\3\2\2\2"+
		"\22\u00fa\3\2\2\2\24\u00fc\3\2\2\2\26\u0103\3\2\2\2\30\u0105\3\2\2\2\32"+
		"\u010e\3\2\2\2\34\u0110\3\2\2\2\36\u0114\3\2\2\2 \u0116\3\2\2\2\"\u0129"+
		"\3\2\2\2$\u012e\3\2\2\2&\u0131\3\2\2\2(\u0133\3\2\2\2*\u0135\3\2\2\2,"+
		"\u0152\3\2\2\2.\u0155\3\2\2\2\60\u0157\3\2\2\2\62\u015c\3\2\2\2\64\u0166"+
		"\3\2\2\2\66\u016a\3\2\2\28\u0177\3\2\2\2:\u017a\3\2\2\2<\u017c\3\2\2\2"+
		">\u018b\3\2\2\2@\u018e\3\2\2\2B\u0191\3\2\2\2D\u0194\3\2\2\2F\u019c\3"+
		"\2\2\2H\u019e\3\2\2\2J\u01a8\3\2\2\2L\u01b0\3\2\2\2N\u01b6\3\2\2\2P\u01b8"+
		"\3\2\2\2R\u01c2\3\2\2\2T\u01c8\3\2\2\2V\u01cb\3\2\2\2X\u01d5\3\2\2\2Z"+
		"\u01d8\3\2\2\2\\\u01da\3\2\2\2^\u0215\3\2\2\2`\u0235\3\2\2\2b\u0239\3"+
		"\2\2\2d\u023d\3\2\2\2f\u023f\3\2\2\2h\u024e\3\2\2\2j\u0251\3\2\2\2l\u0254"+
		"\3\2\2\2n\u0256\3\2\2\2p\u025e\3\2\2\2r\u0262\3\2\2\2t\u0264\3\2\2\2v"+
		"\u0266\3\2\2\2x\u0271\3\2\2\2z\u0273\3\2\2\2|\u0275\3\2\2\2~\u0277\3\2"+
		"\2\2\u0080\u0279\3\2\2\2\u0082\u028b\3\2\2\2\u0084\u028f\3\2\2\2\u0086"+
		"\u0291\3\2\2\2\u0088\u0295\3\2\2\2\u008a\u0299\3\2\2\2\u008c\u029b\3\2"+
		"\2\2\u008e\u02a5\3\2\2\2\u0090\u02ab\3\2\2\2\u0092\u02ad\3\2\2\2\u0094"+
		"\u02b0\3\2\2\2\u0096\u02bc\3\2\2\2\u0098\u009a\5\4\3\2\u0099\u0098\3\2"+
		"\2\2\u0099\u009a\3\2\2\2\u009a\u009e\3\2\2\2\u009b\u009d\5\f\7\2\u009c"+
		"\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2"+
		"\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a3\5\4\3\2\u00a2"+
		"\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a7\3\2\2\2\u00a4\u00a6\5\22"+
		"\n\2\u00a5\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\7\2"+
		"\2\3\u00ab\u00ad\7\2\2\3\u00ac\u0099\3\2\2\2\u00ac\u00ab\3\2\2\2\u00ad"+
		"\3\3\2\2\2\u00ae\u00af\7\3\2\2\u00af\u00be\5\u0094K\2\u00b0\u00b1\7\4"+
		"\2\2\u00b1\u00b9\5\u0094K\2\u00b2\u00b4\7\5\2\2\u00b3\u00b5\7\6\2\2\u00b4"+
		"\u00b3\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b8\5\u0094"+
		"K\2\u00b7\u00b2\3\2\2\2\u00b8\u00bb\3\2\2\2\u00b9\u00b7\3\2\2\2\u00b9"+
		"\u00ba\3\2\2\2\u00ba\u00bc\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bd\7\7"+
		"\2\2\u00bd\u00bf\3\2\2\2\u00be\u00b0\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf"+
		"\5\3\2\2\2\u00c0\u00c2\5J&\2\u00c1\u00c0\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\7\3\2\2\2\u00c3\u00c4\7\b\2\2\u00c4\u00c6\5\u0094K\2\u00c5\u00c3\3\2"+
		"\2\2\u00c5\u00c6\3\2\2\2\u00c6\t\3\2\2\2\u00c7\u00c8\7\4\2\2\u00c8\u00c9"+
		"\5\6\4\2\u00c9\u00ca\7\7\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c7\3\2\2\2\u00cb"+
		"\u00cc\3\2\2\2\u00cc\13\3\2\2\2\u00cd\u00ce\5D#\2\u00ce\u00cf\7\t\2\2"+
		"\u00cf\u00d0\5\u0094K\2\u00d0\u00d1\5\n\6\2\u00d1\u00d2\5\b\5\2\u00d2"+
		"\r\3\2\2\2\u00d3\u00d5\7\n\2\2\u00d4\u00d3\3\2\2\2\u00d4\u00d5\3\2\2\2"+
		"\u00d5\u00d6\3\2\2\2\u00d6\u00de\7\13\2\2\u00d7\u00d9\5^\60\2\u00d8\u00d7"+
		"\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\u00df\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00df\5> \2\u00de\u00da\3\2\2"+
		"\2\u00de\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e7\7\f\2\2\u00e1\u00e4"+
		"\7\n\2\2\u00e2\u00e5\5^\60\2\u00e3\u00e5\5> \2\u00e4\u00e2\3\2\2\2\u00e4"+
		"\u00e3\3\2\2\2\u00e5\u00e7\3\2\2\2\u00e6\u00d4\3\2\2\2\u00e6\u00e1\3\2"+
		"\2\2\u00e7\17\3\2\2\2\u00e8\u00e9\7\r\2\2\u00e9\u00ee\5\u0094K\2\u00ea"+
		"\u00eb\7\4\2\2\u00eb\u00ec\5J&\2\u00ec\u00ed\7\7\2\2\u00ed\u00ef\3\2\2"+
		"\2\u00ee\u00ea\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1"+
		"\5\16\b\2\u00f1\21\3\2\2\2\u00f2\u00fb\5\24\13\2\u00f3\u00fb\5\30\r\2"+
		"\u00f4\u00fb\5 \21\2\u00f5\u00fb\5*\26\2\u00f6\u00fb\5L\'\2\u00f7\u00fb"+
		"\5H%\2\u00f8\u00fb\5\60\31\2\u00f9\u00fb\5\20\t\2\u00fa\u00f2\3\2\2\2"+
		"\u00fa\u00f3\3\2\2\2\u00fa\u00f4\3\2\2\2\u00fa\u00f5\3\2\2\2\u00fa\u00f6"+
		"\3\2\2\2\u00fa\u00f7\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00f9\3\2\2\2\u00fb"+
		"\23\3\2\2\2\u00fc\u00fe\7\16\2\2\u00fd\u00ff\5\u0094K\2\u00fe\u00fd\3"+
		"\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0101\5\u008cG\2"+
		"\u0101\25\3\2\2\2\u0102\u0104\5\u0094K\2\u0103\u0102\3\2\2\2\u0103\u0104"+
		"\3\2\2\2\u0104\27\3\2\2\2\u0105\u0106\7\17\2\2\u0106\u0107\5\26\f\2\u0107"+
		"\u0108\5\u008cG\2\u0108\31\3\2\2\2\u0109\u010a\5\u0094K\2\u010a\u010b"+
		"\7\20\2\2\u010b\u010c\7T\2\2\u010c\u010f\3\2\2\2\u010d\u010f\7T\2\2\u010e"+
		"\u0109\3\2\2\2\u010e\u010d\3\2\2\2\u010f\33\3\2\2\2\u0110\u0111\7\13\2"+
		"\2\u0111\u0112\5> \2\u0112\u0113\7\f\2\2\u0113\35\3\2\2\2\u0114\u0115"+
		"\5^\60\2\u0115\37\3\2\2\2\u0116\u0117\5D#\2\u0117\u0118\7\21\2\2\u0118"+
		"\u0119\5\32\16\2\u0119\u011a\5.\30\2\u011a\u011b\7\22\2\2\u011b\u011c"+
		"\5^\60\2\u011c\u011d\7\13\2\2\u011d\u011e\5\36\20\2\u011e\u011f\7\f\2"+
		"\2\u011f!\3\2\2\2\u0120\u0123\5\u0094K\2\u0121\u0123\5\u008cG\2\u0122"+
		"\u0120\3\2\2\2\u0122\u0121\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u012a\3\2"+
		"\2\2\u0124\u0125\5\32\16\2\u0125\u0126\5\u008cG\2\u0126\u0128\3\2\2\2"+
		"\u0127\u0124\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u012a\3\2\2\2\u0129\u0122"+
		"\3\2\2\2\u0129\u0127\3\2\2\2\u012a#\3\2\2\2\u012b\u012c\5\u0094K\2\u012c"+
		"\u012d\7\22\2\2\u012d\u012f\3\2\2\2\u012e\u012b\3\2\2\2\u012e\u012f\3"+
		"\2\2\2\u012f%\3\2\2\2\u0130\u0132\58\35\2\u0131\u0130\3\2\2\2\u0131\u0132"+
		"\3\2\2\2\u0132\'\3\2\2\2\u0133\u0134\t\2\2\2\u0134)\3\2\2\2\u0135\u0136"+
		"\5$\23\2\u0136\u0137\5(\25\2\u0137\u0138\5\"\22\2\u0138\u0139\5&\24\2"+
		"\u0139+\3\2\2\2\u013a\u0143\7\25\2\2\u013b\u0140\5n8\2\u013c\u013d\7\5"+
		"\2\2\u013d\u013f\5n8\2\u013e\u013c\3\2\2\2\u013f\u0142\3\2\2\2\u0140\u013e"+
		"\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0143"+
		"\u013b\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\3\2\2\2\u0145\u0153\7\26"+
		"\2\2\u0146\u014f\7\4\2\2\u0147\u014c\5n8\2\u0148\u0149\7\5\2\2\u0149\u014b"+
		"\5n8\2\u014a\u0148\3\2\2\2\u014b\u014e\3\2\2\2\u014c\u014a\3\2\2\2\u014c"+
		"\u014d\3\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014f\u0147\3\2"+
		"\2\2\u014f\u0150\3\2\2\2\u0150\u0151\3\2\2\2\u0151\u0153\7\7\2\2\u0152"+
		"\u013a\3\2\2\2\u0152\u0146\3\2\2\2\u0153-\3\2\2\2\u0154\u0156\5,\27\2"+
		"\u0155\u0154\3\2\2\2\u0155\u0156\3\2\2\2\u0156/\3\2\2\2\u0157\u0158\7"+
		"\27\2\2\u0158\u0159\5\32\16\2\u0159\u015a\5.\30\2\u015a\u015b\5\u008c"+
		"G\2\u015b\61\3\2\2\2\u015c\u0161\5<\37\2\u015d\u015e\7\5\2\2\u015e\u0160"+
		"\5<\37\2\u015f\u015d\3\2\2\2\u0160\u0163\3\2\2\2\u0161\u015f\3\2\2\2\u0161"+
		"\u0162\3\2\2\2\u0162\63\3\2\2\2\u0163\u0161\3\2\2\2\u0164\u0165\7\30\2"+
		"\2\u0165\u0167\5\62\32\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167"+
		"\65\3\2\2\2\u0168\u0169\7\31\2\2\u0169\u016b\7S\2\2\u016a\u0168\3\2\2"+
		"\2\u016a\u016b\3\2\2\2\u016b\67\3\2\2\2\u016c\u016d\7\32\2\2\u016d\u016e"+
		"\5\u0096L\2\u016e\u016f\5\64\33\2\u016f\u0170\5\66\34\2\u0170\u0178\3"+
		"\2\2\2\u0171\u0172\7\32\2\2\u0172\u0173\5\62\32\2\u0173\u0174\5\66\34"+
		"\2\u0174\u0178\3\2\2\2\u0175\u0176\7\31\2\2\u0176\u0178\7S\2\2\u0177\u016c"+
		"\3\2\2\2\u0177\u0171\3\2\2\2\u0177\u0175\3\2\2\2\u01789\3\2\2\2\u0179"+
		"\u017b\7\6\2\2\u017a\u0179\3\2\2\2\u017a\u017b\3\2\2\2\u017b;\3\2\2\2"+
		"\u017c\u017d\5:\36\2\u017d\u0181\5\u0096L\2\u017e\u0182\5\u0094K\2\u017f"+
		"\u0182\7\33\2\2\u0180\u0182\7\34\2\2\u0181\u017e\3\2\2\2\u0181\u017f\3"+
		"\2\2\2\u0181\u0180\3\2\2\2\u0182=\3\2\2\2\u0183\u0188\5n8\2\u0184\u0185"+
		"\7\5\2\2\u0185\u0187\5n8\2\u0186\u0184\3\2\2\2\u0187\u018a\3\2\2\2\u0188"+
		"\u0186\3\2\2\2\u0188\u0189\3\2\2\2\u0189\u018c\3\2\2\2\u018a\u0188\3\2"+
		"\2\2\u018b\u0183\3\2\2\2\u018b\u018c\3\2\2\2\u018c?\3\2\2\2\u018d\u018f"+
		"\5N(\2\u018e\u018d\3\2\2\2\u018e\u018f\3\2\2\2\u018fA\3\2\2\2\u0190\u0192"+
		"\7\35\2\2\u0191\u0190\3\2\2\2\u0191\u0192\3\2\2\2\u0192C\3\2\2\2\u0193"+
		"\u0195\7\36\2\2\u0194\u0193\3\2\2\2\u0194\u0195\3\2\2\2\u0195E\3\2\2\2"+
		"\u0196\u0197\5B\"\2\u0197\u0198\5@!\2\u0198\u019d\3\2\2\2\u0199\u019a"+
		"\5@!\2\u019a\u019b\5B\"\2\u019b\u019d\3\2\2\2\u019c\u0196\3\2\2\2\u019c"+
		"\u0199\3\2\2\2\u019dG\3\2\2\2\u019e\u019f\5D#\2\u019f\u01a0\5F$\2\u01a0"+
		"\u01a1\7\37\2\2\u01a1\u01a2\5J&\2\u01a2\u01a3\5V,\2\u01a3\u01a4\7\13\2"+
		"\2\u01a4\u01a5\5> \2\u01a5\u01a6\7\f\2\2\u01a6\u01a7\5\u008eH\2\u01a7"+
		"I\3\2\2\2\u01a8\u01ad\5\u0094K\2\u01a9\u01aa\7\5\2\2\u01aa\u01ac\5\u0094"+
		"K\2\u01ab\u01a9\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad"+
		"\u01ae\3\2\2\2\u01aeK\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b1\7 \2\2\u01b1"+
		"\u01b2\5\u0094K\2\u01b2\u01b3\7\13\2\2\u01b3\u01b4\5J&\2\u01b4\u01b5\7"+
		"\f\2\2\u01b5M\3\2\2\2\u01b6\u01b7\t\3\2\2\u01b7O\3\2\2\2\u01b8\u01bd\5"+
		"\u0094K\2\u01b9\u01ba\7$\2\2\u01ba\u01bc\5\u0094K\2\u01bb\u01b9\3\2\2"+
		"\2\u01bc\u01bf\3\2\2\2\u01bd\u01be\3\2\2\2\u01bd\u01bb\3\2\2\2\u01beQ"+
		"\3\2\2\2\u01bf\u01bd\3\2\2\2\u01c0\u01c3\5\u0094K\2\u01c1\u01c3\5P)\2"+
		"\u01c2\u01c0\3\2\2\2\u01c2\u01c1\3\2\2\2\u01c3S\3\2\2\2\u01c4\u01c5\7"+
		"%\2\2\u01c5\u01c9\5\u0094K\2\u01c6\u01c7\7&\2\2\u01c7\u01c9\5R*\2\u01c8"+
		"\u01c4\3\2\2\2\u01c8\u01c6\3\2\2\2\u01c9U\3\2\2\2\u01ca\u01cc\5T+\2\u01cb"+
		"\u01ca\3\2\2\2\u01cb\u01cc\3\2\2\2\u01ccW\3\2\2\2\u01cd\u01d2\5^\60\2"+
		"\u01ce\u01cf\7\5\2\2\u01cf\u01d1\5^\60\2\u01d0\u01ce\3\2\2\2\u01d1\u01d4"+
		"\3\2\2\2\u01d2\u01d0\3\2\2\2\u01d2\u01d3\3\2\2\2\u01d3\u01d6\3\2\2\2\u01d4"+
		"\u01d2\3\2\2\2\u01d5\u01cd\3\2\2\2\u01d5\u01d6\3\2\2\2\u01d6Y\3\2\2\2"+
		"\u01d7\u01d9\t\4\2\2\u01d8\u01d7\3\2\2\2\u01d8\u01d9\3\2\2\2\u01d9[\3"+
		"\2\2\2\u01da\u01df\5n8\2\u01db\u01dc\7\5\2\2\u01dc\u01de\5n8\2\u01dd\u01db"+
		"\3\2\2\2\u01de\u01e1\3\2\2\2\u01df\u01dd\3\2\2\2\u01df\u01e0\3\2\2\2\u01e0"+
		"]\3\2\2\2\u01e1\u01df\3\2\2\2\u01e2\u01e3\b\60\1\2\u01e3\u01e4\7\r\2\2"+
		"\u01e4\u01e9\5p9\2\u01e5\u01e6\7\5\2\2\u01e6\u01e8\5p9\2\u01e7\u01e5\3"+
		"\2\2\2\u01e8\u01eb\3\2\2\2\u01e9\u01e7\3\2\2\2\u01e9\u01ea\3\2\2\2\u01ea"+
		"\u01ec\3\2\2\2\u01eb\u01e9\3\2\2\2\u01ec\u01ed\5\u0090I\2\u01ed\u0216"+
		"\3\2\2\2\u01ee\u01ef\5r:\2\u01ef\u01f0\5\\/\2\u01f0\u01f1\5\u0090I\2\u01f1"+
		"\u0216\3\2\2\2\u01f2\u01f3\5x=\2\u01f3\u01f4\5^\60\25\u01f4\u0216\3\2"+
		"\2\2\u01f5\u0216\5f\64\2\u01f6\u0216\5d\63\2\u01f7\u0216\7\34\2\2\u01f8"+
		"\u0216\7,\2\2\u01f9\u01fa\7\25\2\2\u01fa\u01fb\5^\60\2\u01fb\u01fc\7\26"+
		"\2\2\u01fc\u0216\3\2\2\2\u01fd\u0216\5\u0094K\2\u01fe\u01ff\7-\2\2\u01ff"+
		"\u0216\5\u0094K\2\u0200\u0216\7.\2\2\u0201\u0216\5\u008cG\2\u0202\u0203"+
		"\5r:\2\u0203\u0204\5^\60\2\u0204\u0205\5\u0082B\2\u0205\u0206\5^\60\6"+
		"\u0206\u0216\3\2\2\2\u0207\u0208\7\13\2\2\u0208\u020d\5n8\2\u0209\u020a"+
		"\7\5\2\2\u020a\u020c\5n8\2\u020b\u0209\3\2\2\2\u020c\u020f\3\2\2\2\u020d"+
		"\u020b\3\2\2\2\u020d\u020e\3\2\2\2\u020e\u0210\3\2\2\2\u020f\u020d\3\2"+
		"\2\2\u0210\u0211\5\u0090I\2\u0211\u0212\7\f\2\2\u0212\u0216\3\2\2\2\u0213"+
		"\u0216\5\34\17\2\u0214\u0216\7Y\2\2\u0215\u01e2\3\2\2\2\u0215\u01ee\3"+
		"\2\2\2\u0215\u01f2\3\2\2\2\u0215\u01f5\3\2\2\2\u0215\u01f6\3\2\2\2\u0215"+
		"\u01f7\3\2\2\2\u0215\u01f8\3\2\2\2\u0215\u01f9\3\2\2\2\u0215\u01fd\3\2"+
		"\2\2\u0215\u01fe\3\2\2\2\u0215\u0200\3\2\2\2\u0215\u0201\3\2\2\2\u0215"+
		"\u0202\3\2\2\2\u0215\u0207\3\2\2\2\u0215\u0213\3\2\2\2\u0215\u0214\3\2"+
		"\2\2\u0216\u0231\3\2\2\2\u0217\u0218\f\24\2\2\u0218\u0219\5\u0082B\2\u0219"+
		"\u021a\5^\60\25\u021a\u0230\3\2\2\2\u021b\u021c\f\23\2\2\u021c\u021d\5"+
		"\u0086D\2\u021d\u021e\5^\60\24\u021e\u0230\3\2\2\2\u021f\u0220\f\22\2"+
		"\2\u0220\u0221\5Z.\2\u0221\u0222\5\u008aF\2\u0222\u0223\5^\60\23\u0223"+
		"\u0230\3\2\2\2\u0224\u0225\f\21\2\2\u0225\u0226\t\5\2\2\u0226\u0227\5"+
		"^\60\2\u0227\u0228\7+\2\2\u0228\u0229\5^\60\22\u0229\u0230\3\2\2\2\u022a"+
		"\u022b\f\20\2\2\u022b\u022c\7\4\2\2\u022c\u022d\5X-\2\u022d\u022e\7\7"+
		"\2\2\u022e\u0230\3\2\2\2\u022f\u0217\3\2\2\2\u022f\u021b\3\2\2\2\u022f"+
		"\u021f\3\2\2\2\u022f\u0224\3\2\2\2\u022f\u022a\3\2\2\2\u0230\u0233\3\2"+
		"\2\2\u0231\u022f\3\2\2\2\u0231\u0232\3\2\2\2\u0232_\3\2\2\2\u0233\u0231"+
		"\3\2\2\2\u0234\u0236\7/\2\2\u0235\u0234\3\2\2\2\u0235\u0236\3\2\2\2\u0236"+
		"\u0237\3\2\2\2\u0237\u0238\5\u0096L\2\u0238a\3\2\2\2\u0239\u023a\t\6\2"+
		"\2\u023ac\3\2\2\2\u023b\u023e\5`\61\2\u023c\u023e\5b\62\2\u023d\u023b"+
		"\3\2\2\2\u023d\u023c\3\2\2\2\u023ee\3\2\2\2\u023f\u0240\7\63\2\2\u0240"+
		"\u0249\7\4\2\2\u0241\u0246\5^\60\2\u0242\u0243\7\5\2\2\u0243\u0245\5^"+
		"\60\2\u0244\u0242\3\2\2\2\u0245\u0248\3\2\2\2\u0246\u0244\3\2\2\2\u0246"+
		"\u0247\3\2\2\2\u0247\u024a\3\2\2\2\u0248\u0246\3\2\2\2\u0249\u0241\3\2"+
		"\2\2\u0249\u024a\3\2\2\2\u024a\u024b\3\2\2\2\u024b\u024c\7\7\2\2\u024c"+
		"g\3\2\2\2\u024d\u024f\t\7\2\2\u024e\u024d\3\2\2\2\u024e\u024f\3\2\2\2"+
		"\u024fi\3\2\2\2\u0250\u0252\7\63\2\2\u0251\u0250\3\2\2\2\u0251\u0252\3"+
		"\2\2\2\u0252k\3\2\2\2\u0253\u0255\7\5\2\2\u0254\u0253\3\2\2\2\u0254\u0255"+
		"\3\2\2\2\u0255m\3\2\2\2\u0256\u0257\5D#\2\u0257\u0258\5h\65\2\u0258\u0259"+
		"\5J&\2\u0259\u025a\7\22\2\2\u025a\u025b\5j\66\2\u025b\u025c\5^\60\2\u025c"+
		"\u025d\5l\67\2\u025do\3\2\2\2\u025e\u025f\5\u0094K\2\u025f\u0260\7\n\2"+
		"\2\u0260\u0261\5^\60\2\u0261q\3\2\2\2\u0262\u0263\t\b\2\2\u0263s\3\2\2"+
		"\2\u0264\u0265\78\2\2\u0265u\3\2\2\2\u0266\u0267\t\t\2\2\u0267w\3\2\2"+
		"\2\u0268\u0272\7\'\2\2\u0269\u0272\7(\2\2\u026a\u0272\7\66\2\2\u026b\u0272"+
		"\5N(\2\u026c\u0272\7;\2\2\u026d\u0272\5t;\2\u026e\u0272\7<\2\2\u026f\u0272"+
		"\5v<\2\u0270\u0272\7\33\2\2\u0271\u0268\3\2\2\2\u0271\u0269\3\2\2\2\u0271"+
		"\u026a\3\2\2\2\u0271\u026b\3\2\2\2\u0271\u026c\3\2\2\2\u0271\u026d\3\2"+
		"\2\2\u0271\u026e\3\2\2\2\u0271\u026f\3\2\2\2\u0271\u0270\3\2\2\2\u0272"+
		"y\3\2\2\2\u0273\u0274\t\n\2\2\u0274{\3\2\2\2\u0275\u0276\7\20\2\2\u0276"+
		"}\3\2\2\2\u0277\u0278\7$\2\2\u0278\177\3\2\2\2\u0279\u027a\7/\2\2\u027a"+
		"\u0081\3\2\2\2\u027b\u028c\7@\2\2\u027c\u028c\7A\2\2\u027d\u028c\7B\2"+
		"\2\u027e\u028c\7C\2\2\u027f\u028c\7D\2\2\u0280\u028c\7E\2\2\u0281\u028c"+
		"\7)\2\2\u0282\u028c\7*\2\2\u0283\u028c\7F\2\2\u0284\u028c\5~@\2\u0285"+
		"\u028c\5\u0080A\2\u0286\u028c\7G\2\2\u0287\u028c\7H\2\2\u0288\u028c\7"+
		"I\2\2\u0289\u028c\5|?\2\u028a\u028c\5z>\2\u028b\u027b\3\2\2\2\u028b\u027c"+
		"\3\2\2\2\u028b\u027d\3\2\2\2\u028b\u027e\3\2\2\2\u028b\u027f\3\2\2\2\u028b"+
		"\u0280\3\2\2\2\u028b\u0281\3\2\2\2\u028b\u0282\3\2\2\2\u028b\u0283\3\2"+
		"\2\2\u028b\u0284\3\2\2\2\u028b\u0285\3\2\2\2\u028b\u0286\3\2\2\2\u028b"+
		"\u0287\3\2\2\2\u028b\u0288\3\2\2\2\u028b\u0289\3\2\2\2\u028b\u028a\3\2"+
		"\2\2\u028c\u0083\3\2\2\2\u028d\u0290\5N(\2\u028e\u0290\7;\2\2\u028f\u028d"+
		"\3\2\2\2\u028f\u028e\3\2\2\2\u028f\u0290\3\2\2\2\u0290\u0085\3\2\2\2\u0291"+
		"\u0292\5\u0084C\2\u0292\u0293\7J\2\2\u0293\u0294\5\u0084C\2\u0294\u0087"+
		"\3\2\2\2\u0295\u0296\t\13\2\2\u0296\u0089\3\2\2\2\u0297\u029a\5\u0088"+
		"E\2\u0298\u029a\7&\2\2\u0299\u0297\3\2\2\2\u0299\u0298\3\2\2\2\u029a\u008b"+
		"\3\2\2\2\u029b\u029f\7\13\2\2\u029c\u029e\5^\60\2\u029d\u029c\3\2\2\2"+
		"\u029e\u02a1\3\2\2\2\u029f\u029d\3\2\2\2\u029f\u02a0\3\2\2\2\u02a0\u02a2"+
		"\3\2\2\2\u02a1\u029f\3\2\2\2\u02a2\u02a3\7\f\2\2\u02a3\u008d\3\2\2\2\u02a4"+
		"\u02a6\5\u008cG\2\u02a5\u02a4\3\2\2\2\u02a5\u02a6\3\2\2\2\u02a6\u008f"+
		"\3\2\2\2\u02a7\u02ac\5\u008cG\2\u02a8\u02a9\5\u0092J\2\u02a9\u02aa\5^"+
		"\60\2\u02aa\u02ac\3\2\2\2\u02ab\u02a7\3\2\2\2\u02ab\u02a8\3\2\2\2\u02ac"+
		"\u0091\3\2\2\2\u02ad\u02ae\7P\2\2\u02ae\u0093\3\2\2\2\u02af\u02b1\7Q\2"+
		"\2\u02b0\u02af\3\2\2\2\u02b0\u02b1\3\2\2\2\u02b1\u02b6\3\2\2\2\u02b2\u02b3"+
		"\7T\2\2\u02b3\u02b5\7R\2\2\u02b4\u02b2\3\2\2\2\u02b5\u02b8\3\2\2\2\u02b6"+
		"\u02b4\3\2\2\2\u02b6\u02b7\3\2\2\2\u02b7\u02b9\3\2\2\2\u02b8\u02b6\3\2"+
		"\2\2\u02b9\u02ba\7T\2\2\u02ba\u0095\3\2\2\2\u02bb\u02bd\7S\2\2\u02bc\u02bb"+
		"\3\2\2\2\u02bd\u02be\3\2\2\2\u02be\u02bc\3\2\2\2\u02be\u02bf\3\2\2\2\u02bf"+
		"\u0097\3\2\2\2M\u0099\u009e\u00a2\u00a7\u00ac\u00b4\u00b9\u00be\u00c1"+
		"\u00c5\u00cb\u00d4\u00da\u00de\u00e4\u00e6\u00ee\u00fa\u00fe\u0103\u010e"+
		"\u0122\u0127\u0129\u012e\u0131\u0140\u0143\u014c\u014f\u0152\u0155\u0161"+
		"\u0166\u016a\u0177\u017a\u0181\u0188\u018b\u018e\u0191\u0194\u019c\u01ad"+
		"\u01bd\u01c2\u01c8\u01cb\u01d2\u01d5\u01d8\u01df\u01e9\u020d\u0215\u022f"+
		"\u0231\u0235\u023d\u0246\u0249\u024e\u0251\u0254\u0271\u028b\u028f\u0299"+
		"\u029f\u02a5\u02ab\u02b0\u02b6\u02be";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}