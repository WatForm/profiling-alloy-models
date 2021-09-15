// Generated from C:/Users/eeid/IdeaProjects/alloy-profiling/src/main/antlr/com/alloyprofiling\ALLOY.g4 by ANTLR 4.9.1

 package com.alloyprofiling;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ALLOYParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ALLOYVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#specification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSpecification(ALLOYParser.SpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(ALLOYParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#names_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNames_opt(ALLOYParser.Names_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#as_name_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAs_name_opt(ALLOYParser.As_name_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#para_open}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPara_open(ALLOYParser.Para_openContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#open}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpen(ALLOYParser.OpenContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#macro_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro_expr(ALLOYParser.Macro_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#macro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMacro(ALLOYParser.MacroContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#paragraph}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParagraph(ALLOYParser.ParagraphContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#factDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactDecl(ALLOYParser.FactDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#name_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName_opt(ALLOYParser.Name_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#assertDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertDecl(ALLOYParser.AssertDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#nameID}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameID(ALLOYParser.NameIDContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#decls_f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls_f(ALLOYParser.Decls_fContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#funExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunExpr(ALLOYParser.FunExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#funDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunDecl(ALLOYParser.FunDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#nameOrBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameOrBlock(ALLOYParser.NameOrBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#name_cmd_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName_cmd_opt(ALLOYParser.Name_cmd_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#scope_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScope_opt(ALLOYParser.Scope_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#run_or_check}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRun_or_check(ALLOYParser.Run_or_checkContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#cmdDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCmdDecl(ALLOYParser.CmdDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#paraDecls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParaDecls(ALLOYParser.ParaDeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#paraDecls_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParaDecls_opt(ALLOYParser.ParaDecls_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#predDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredDecl(ALLOYParser.PredDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#typescopes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypescopes(ALLOYParser.TypescopesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#but_typescopes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBut_typescopes(ALLOYParser.But_typescopesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#expect_digit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpect_digit(ALLOYParser.Expect_digitContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#scope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScope(ALLOYParser.ScopeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#exactly_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExactly_opt(ALLOYParser.Exactly_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#typescope}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypescope(ALLOYParser.TypescopeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#decls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls(ALLOYParser.DeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#multiplicity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicity(ALLOYParser.MultiplicityContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#abs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbs(ALLOYParser.AbsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#priv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPriv(ALLOYParser.PrivContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#abs_multiplicity}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbs_multiplicity(ALLOYParser.Abs_multiplicityContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#sigDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigDecl(ALLOYParser.SigDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#names}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNames(ALLOYParser.NamesContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#enumDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDecl(ALLOYParser.EnumDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#mult}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult(ALLOYParser.MultContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#union}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(ALLOYParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#superSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSet(ALLOYParser.SuperSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#sigExt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigExt(ALLOYParser.SigExtContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#sigExtension}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSigExtension(ALLOYParser.SigExtensionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#exprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprs(ALLOYParser.ExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#notOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotOp(ALLOYParser.NotOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#decls_e}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls_e(ALLOYParser.Decls_eContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(ALLOYParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#num}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNum(ALLOYParser.NumContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#const_sets}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConst_sets(ALLOYParser.Const_setsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(ALLOYParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#disjFunc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjFunc(ALLOYParser.DisjFuncContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#disjoint}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisjoint(ALLOYParser.DisjointContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#disj}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDisj(ALLOYParser.DisjContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#comma_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComma_opt(ALLOYParser.Comma_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(ALLOYParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#letDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetDecl(ALLOYParser.LetDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#quant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuant(ALLOYParser.QuantContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#setCard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetCard(ALLOYParser.SetCardContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#tcOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTcOp(ALLOYParser.TcOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#unOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnOp(ALLOYParser.UnOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#bit_shifter_operators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBit_shifter_operators(ALLOYParser.Bit_shifter_operatorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#dotOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDotOp(ALLOYParser.DotOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#add}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdd(ALLOYParser.AddContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#sub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSub(ALLOYParser.SubContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#binOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinOp(ALLOYParser.BinOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#mult_or_set}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult_or_set(ALLOYParser.Mult_or_setContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#arrowOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrowOp(ALLOYParser.ArrowOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#rel_operators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRel_operators(ALLOYParser.Rel_operatorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#compareOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompareOp(ALLOYParser.CompareOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(ALLOYParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#block_opt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_opt(ALLOYParser.Block_optContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#blockOrBar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockOrBar(ALLOYParser.BlockOrBarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#bar}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBar(ALLOYParser.BarContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(ALLOYParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link ALLOYParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(ALLOYParser.NumberContext ctx);
}