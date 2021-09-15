// Generated from C:/Users/eeid/IdeaProjects/alloy-profiling/src/main/antlr/com/alloyprofiling\ALLOY.g4 by ANTLR 4.9.1

 package com.alloyprofiling;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ALLOYParser}.
 */
public interface ALLOYListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#specification}.
	 * @param ctx the parse tree
	 */
	void enterSpecification(ALLOYParser.SpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#specification}.
	 * @param ctx the parse tree
	 */
	void exitSpecification(ALLOYParser.SpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#module}.
	 * @param ctx the parse tree
	 */
	void enterModule(ALLOYParser.ModuleContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#module}.
	 * @param ctx the parse tree
	 */
	void exitModule(ALLOYParser.ModuleContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#names_opt}.
	 * @param ctx the parse tree
	 */
	void enterNames_opt(ALLOYParser.Names_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#names_opt}.
	 * @param ctx the parse tree
	 */
	void exitNames_opt(ALLOYParser.Names_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#as_name_opt}.
	 * @param ctx the parse tree
	 */
	void enterAs_name_opt(ALLOYParser.As_name_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#as_name_opt}.
	 * @param ctx the parse tree
	 */
	void exitAs_name_opt(ALLOYParser.As_name_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#para_open}.
	 * @param ctx the parse tree
	 */
	void enterPara_open(ALLOYParser.Para_openContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#para_open}.
	 * @param ctx the parse tree
	 */
	void exitPara_open(ALLOYParser.Para_openContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#open}.
	 * @param ctx the parse tree
	 */
	void enterOpen(ALLOYParser.OpenContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#open}.
	 * @param ctx the parse tree
	 */
	void exitOpen(ALLOYParser.OpenContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#macro_expr}.
	 * @param ctx the parse tree
	 */
	void enterMacro_expr(ALLOYParser.Macro_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#macro_expr}.
	 * @param ctx the parse tree
	 */
	void exitMacro_expr(ALLOYParser.Macro_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#macro}.
	 * @param ctx the parse tree
	 */
	void enterMacro(ALLOYParser.MacroContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#macro}.
	 * @param ctx the parse tree
	 */
	void exitMacro(ALLOYParser.MacroContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#paragraph}.
	 * @param ctx the parse tree
	 */
	void enterParagraph(ALLOYParser.ParagraphContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#paragraph}.
	 * @param ctx the parse tree
	 */
	void exitParagraph(ALLOYParser.ParagraphContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#factDecl}.
	 * @param ctx the parse tree
	 */
	void enterFactDecl(ALLOYParser.FactDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#factDecl}.
	 * @param ctx the parse tree
	 */
	void exitFactDecl(ALLOYParser.FactDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#name_opt}.
	 * @param ctx the parse tree
	 */
	void enterName_opt(ALLOYParser.Name_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#name_opt}.
	 * @param ctx the parse tree
	 */
	void exitName_opt(ALLOYParser.Name_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#assertDecl}.
	 * @param ctx the parse tree
	 */
	void enterAssertDecl(ALLOYParser.AssertDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#assertDecl}.
	 * @param ctx the parse tree
	 */
	void exitAssertDecl(ALLOYParser.AssertDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#nameID}.
	 * @param ctx the parse tree
	 */
	void enterNameID(ALLOYParser.NameIDContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#nameID}.
	 * @param ctx the parse tree
	 */
	void exitNameID(ALLOYParser.NameIDContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#decls_f}.
	 * @param ctx the parse tree
	 */
	void enterDecls_f(ALLOYParser.Decls_fContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#decls_f}.
	 * @param ctx the parse tree
	 */
	void exitDecls_f(ALLOYParser.Decls_fContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#funExpr}.
	 * @param ctx the parse tree
	 */
	void enterFunExpr(ALLOYParser.FunExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#funExpr}.
	 * @param ctx the parse tree
	 */
	void exitFunExpr(ALLOYParser.FunExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void enterFunDecl(ALLOYParser.FunDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#funDecl}.
	 * @param ctx the parse tree
	 */
	void exitFunDecl(ALLOYParser.FunDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#nameOrBlock}.
	 * @param ctx the parse tree
	 */
	void enterNameOrBlock(ALLOYParser.NameOrBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#nameOrBlock}.
	 * @param ctx the parse tree
	 */
	void exitNameOrBlock(ALLOYParser.NameOrBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#name_cmd_opt}.
	 * @param ctx the parse tree
	 */
	void enterName_cmd_opt(ALLOYParser.Name_cmd_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#name_cmd_opt}.
	 * @param ctx the parse tree
	 */
	void exitName_cmd_opt(ALLOYParser.Name_cmd_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#scope_opt}.
	 * @param ctx the parse tree
	 */
	void enterScope_opt(ALLOYParser.Scope_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#scope_opt}.
	 * @param ctx the parse tree
	 */
	void exitScope_opt(ALLOYParser.Scope_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#run_or_check}.
	 * @param ctx the parse tree
	 */
	void enterRun_or_check(ALLOYParser.Run_or_checkContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#run_or_check}.
	 * @param ctx the parse tree
	 */
	void exitRun_or_check(ALLOYParser.Run_or_checkContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#cmdDecl}.
	 * @param ctx the parse tree
	 */
	void enterCmdDecl(ALLOYParser.CmdDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#cmdDecl}.
	 * @param ctx the parse tree
	 */
	void exitCmdDecl(ALLOYParser.CmdDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#paraDecls}.
	 * @param ctx the parse tree
	 */
	void enterParaDecls(ALLOYParser.ParaDeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#paraDecls}.
	 * @param ctx the parse tree
	 */
	void exitParaDecls(ALLOYParser.ParaDeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#paraDecls_opt}.
	 * @param ctx the parse tree
	 */
	void enterParaDecls_opt(ALLOYParser.ParaDecls_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#paraDecls_opt}.
	 * @param ctx the parse tree
	 */
	void exitParaDecls_opt(ALLOYParser.ParaDecls_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#predDecl}.
	 * @param ctx the parse tree
	 */
	void enterPredDecl(ALLOYParser.PredDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#predDecl}.
	 * @param ctx the parse tree
	 */
	void exitPredDecl(ALLOYParser.PredDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#typescopes}.
	 * @param ctx the parse tree
	 */
	void enterTypescopes(ALLOYParser.TypescopesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#typescopes}.
	 * @param ctx the parse tree
	 */
	void exitTypescopes(ALLOYParser.TypescopesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#but_typescopes}.
	 * @param ctx the parse tree
	 */
	void enterBut_typescopes(ALLOYParser.But_typescopesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#but_typescopes}.
	 * @param ctx the parse tree
	 */
	void exitBut_typescopes(ALLOYParser.But_typescopesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#expect_digit}.
	 * @param ctx the parse tree
	 */
	void enterExpect_digit(ALLOYParser.Expect_digitContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#expect_digit}.
	 * @param ctx the parse tree
	 */
	void exitExpect_digit(ALLOYParser.Expect_digitContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#scope}.
	 * @param ctx the parse tree
	 */
	void enterScope(ALLOYParser.ScopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#scope}.
	 * @param ctx the parse tree
	 */
	void exitScope(ALLOYParser.ScopeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#exactly_opt}.
	 * @param ctx the parse tree
	 */
	void enterExactly_opt(ALLOYParser.Exactly_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#exactly_opt}.
	 * @param ctx the parse tree
	 */
	void exitExactly_opt(ALLOYParser.Exactly_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#typescope}.
	 * @param ctx the parse tree
	 */
	void enterTypescope(ALLOYParser.TypescopeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#typescope}.
	 * @param ctx the parse tree
	 */
	void exitTypescope(ALLOYParser.TypescopeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#decls}.
	 * @param ctx the parse tree
	 */
	void enterDecls(ALLOYParser.DeclsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#decls}.
	 * @param ctx the parse tree
	 */
	void exitDecls(ALLOYParser.DeclsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#multiplicity}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicity(ALLOYParser.MultiplicityContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#multiplicity}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicity(ALLOYParser.MultiplicityContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#abs}.
	 * @param ctx the parse tree
	 */
	void enterAbs(ALLOYParser.AbsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#abs}.
	 * @param ctx the parse tree
	 */
	void exitAbs(ALLOYParser.AbsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#priv}.
	 * @param ctx the parse tree
	 */
	void enterPriv(ALLOYParser.PrivContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#priv}.
	 * @param ctx the parse tree
	 */
	void exitPriv(ALLOYParser.PrivContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#abs_multiplicity}.
	 * @param ctx the parse tree
	 */
	void enterAbs_multiplicity(ALLOYParser.Abs_multiplicityContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#abs_multiplicity}.
	 * @param ctx the parse tree
	 */
	void exitAbs_multiplicity(ALLOYParser.Abs_multiplicityContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#sigDecl}.
	 * @param ctx the parse tree
	 */
	void enterSigDecl(ALLOYParser.SigDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#sigDecl}.
	 * @param ctx the parse tree
	 */
	void exitSigDecl(ALLOYParser.SigDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#names}.
	 * @param ctx the parse tree
	 */
	void enterNames(ALLOYParser.NamesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#names}.
	 * @param ctx the parse tree
	 */
	void exitNames(ALLOYParser.NamesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#enumDecl}.
	 * @param ctx the parse tree
	 */
	void enterEnumDecl(ALLOYParser.EnumDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#enumDecl}.
	 * @param ctx the parse tree
	 */
	void exitEnumDecl(ALLOYParser.EnumDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#mult}.
	 * @param ctx the parse tree
	 */
	void enterMult(ALLOYParser.MultContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#mult}.
	 * @param ctx the parse tree
	 */
	void exitMult(ALLOYParser.MultContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#union}.
	 * @param ctx the parse tree
	 */
	void enterUnion(ALLOYParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#union}.
	 * @param ctx the parse tree
	 */
	void exitUnion(ALLOYParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#superSet}.
	 * @param ctx the parse tree
	 */
	void enterSuperSet(ALLOYParser.SuperSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#superSet}.
	 * @param ctx the parse tree
	 */
	void exitSuperSet(ALLOYParser.SuperSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#sigExt}.
	 * @param ctx the parse tree
	 */
	void enterSigExt(ALLOYParser.SigExtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#sigExt}.
	 * @param ctx the parse tree
	 */
	void exitSigExt(ALLOYParser.SigExtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#sigExtension}.
	 * @param ctx the parse tree
	 */
	void enterSigExtension(ALLOYParser.SigExtensionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#sigExtension}.
	 * @param ctx the parse tree
	 */
	void exitSigExtension(ALLOYParser.SigExtensionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#exprs}.
	 * @param ctx the parse tree
	 */
	void enterExprs(ALLOYParser.ExprsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#exprs}.
	 * @param ctx the parse tree
	 */
	void exitExprs(ALLOYParser.ExprsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#notOp}.
	 * @param ctx the parse tree
	 */
	void enterNotOp(ALLOYParser.NotOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#notOp}.
	 * @param ctx the parse tree
	 */
	void exitNotOp(ALLOYParser.NotOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#decls_e}.
	 * @param ctx the parse tree
	 */
	void enterDecls_e(ALLOYParser.Decls_eContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#decls_e}.
	 * @param ctx the parse tree
	 */
	void exitDecls_e(ALLOYParser.Decls_eContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(ALLOYParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(ALLOYParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#num}.
	 * @param ctx the parse tree
	 */
	void enterNum(ALLOYParser.NumContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#num}.
	 * @param ctx the parse tree
	 */
	void exitNum(ALLOYParser.NumContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#const_sets}.
	 * @param ctx the parse tree
	 */
	void enterConst_sets(ALLOYParser.Const_setsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#const_sets}.
	 * @param ctx the parse tree
	 */
	void exitConst_sets(ALLOYParser.Const_setsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(ALLOYParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(ALLOYParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#disjFunc}.
	 * @param ctx the parse tree
	 */
	void enterDisjFunc(ALLOYParser.DisjFuncContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#disjFunc}.
	 * @param ctx the parse tree
	 */
	void exitDisjFunc(ALLOYParser.DisjFuncContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#disjoint}.
	 * @param ctx the parse tree
	 */
	void enterDisjoint(ALLOYParser.DisjointContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#disjoint}.
	 * @param ctx the parse tree
	 */
	void exitDisjoint(ALLOYParser.DisjointContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#disj}.
	 * @param ctx the parse tree
	 */
	void enterDisj(ALLOYParser.DisjContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#disj}.
	 * @param ctx the parse tree
	 */
	void exitDisj(ALLOYParser.DisjContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#comma_opt}.
	 * @param ctx the parse tree
	 */
	void enterComma_opt(ALLOYParser.Comma_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#comma_opt}.
	 * @param ctx the parse tree
	 */
	void exitComma_opt(ALLOYParser.Comma_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#decl}.
	 * @param ctx the parse tree
	 */
	void enterDecl(ALLOYParser.DeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#decl}.
	 * @param ctx the parse tree
	 */
	void exitDecl(ALLOYParser.DeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#letDecl}.
	 * @param ctx the parse tree
	 */
	void enterLetDecl(ALLOYParser.LetDeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#letDecl}.
	 * @param ctx the parse tree
	 */
	void exitLetDecl(ALLOYParser.LetDeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#quant}.
	 * @param ctx the parse tree
	 */
	void enterQuant(ALLOYParser.QuantContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#quant}.
	 * @param ctx the parse tree
	 */
	void exitQuant(ALLOYParser.QuantContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#setCard}.
	 * @param ctx the parse tree
	 */
	void enterSetCard(ALLOYParser.SetCardContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#setCard}.
	 * @param ctx the parse tree
	 */
	void exitSetCard(ALLOYParser.SetCardContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#tcOp}.
	 * @param ctx the parse tree
	 */
	void enterTcOp(ALLOYParser.TcOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#tcOp}.
	 * @param ctx the parse tree
	 */
	void exitTcOp(ALLOYParser.TcOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#unOp}.
	 * @param ctx the parse tree
	 */
	void enterUnOp(ALLOYParser.UnOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#unOp}.
	 * @param ctx the parse tree
	 */
	void exitUnOp(ALLOYParser.UnOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#bit_shifter_operators}.
	 * @param ctx the parse tree
	 */
	void enterBit_shifter_operators(ALLOYParser.Bit_shifter_operatorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#bit_shifter_operators}.
	 * @param ctx the parse tree
	 */
	void exitBit_shifter_operators(ALLOYParser.Bit_shifter_operatorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#dotOp}.
	 * @param ctx the parse tree
	 */
	void enterDotOp(ALLOYParser.DotOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#dotOp}.
	 * @param ctx the parse tree
	 */
	void exitDotOp(ALLOYParser.DotOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#add}.
	 * @param ctx the parse tree
	 */
	void enterAdd(ALLOYParser.AddContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#add}.
	 * @param ctx the parse tree
	 */
	void exitAdd(ALLOYParser.AddContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#sub}.
	 * @param ctx the parse tree
	 */
	void enterSub(ALLOYParser.SubContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#sub}.
	 * @param ctx the parse tree
	 */
	void exitSub(ALLOYParser.SubContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#binOp}.
	 * @param ctx the parse tree
	 */
	void enterBinOp(ALLOYParser.BinOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#binOp}.
	 * @param ctx the parse tree
	 */
	void exitBinOp(ALLOYParser.BinOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#mult_or_set}.
	 * @param ctx the parse tree
	 */
	void enterMult_or_set(ALLOYParser.Mult_or_setContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#mult_or_set}.
	 * @param ctx the parse tree
	 */
	void exitMult_or_set(ALLOYParser.Mult_or_setContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#arrowOp}.
	 * @param ctx the parse tree
	 */
	void enterArrowOp(ALLOYParser.ArrowOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#arrowOp}.
	 * @param ctx the parse tree
	 */
	void exitArrowOp(ALLOYParser.ArrowOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#rel_operators}.
	 * @param ctx the parse tree
	 */
	void enterRel_operators(ALLOYParser.Rel_operatorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#rel_operators}.
	 * @param ctx the parse tree
	 */
	void exitRel_operators(ALLOYParser.Rel_operatorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void enterCompareOp(ALLOYParser.CompareOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#compareOp}.
	 * @param ctx the parse tree
	 */
	void exitCompareOp(ALLOYParser.CompareOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ALLOYParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ALLOYParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#block_opt}.
	 * @param ctx the parse tree
	 */
	void enterBlock_opt(ALLOYParser.Block_optContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#block_opt}.
	 * @param ctx the parse tree
	 */
	void exitBlock_opt(ALLOYParser.Block_optContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#blockOrBar}.
	 * @param ctx the parse tree
	 */
	void enterBlockOrBar(ALLOYParser.BlockOrBarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#blockOrBar}.
	 * @param ctx the parse tree
	 */
	void exitBlockOrBar(ALLOYParser.BlockOrBarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#bar}.
	 * @param ctx the parse tree
	 */
	void enterBar(ALLOYParser.BarContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#bar}.
	 * @param ctx the parse tree
	 */
	void exitBar(ALLOYParser.BarContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(ALLOYParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(ALLOYParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link ALLOYParser#number}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ALLOYParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by {@link ALLOYParser#number}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ALLOYParser.NumberContext ctx);
}