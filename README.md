# profiling-alloy-models

This project contains a number of scripts used to investigate the characteristics and patterns of use of Alloy Models.

The scripts are split into 3 categories: 1) Characteristics of Models, 2) Patterns of Use and 3) Analysis Complexity

# Characteristics of Models

These scripts are located in src/main/java/com/alloyprofiling/characteristics

These scripts are split into 5 sub-categories:

  * Length
  * Signatures
  * Constraint holders
  * Corner cases
  * Linear

All these scripts can be run using the ProfilerCharacs.java class located under src/main/java/com/alloyprofiling/characteristics.

### To run a script:

1. Generate profiler-characs.jar using the following **gradle** command if you have gradle installed on your local machine:

   ```shell
   gradle characsJar
   ```

   If you don't have gradle installed, you can use **gradlew**:

   **Linux**:

   ```shell
   ./gradlew characsJar
   ```

   **Windows:**

   ```shell
   .\gradlew.bat characsJar
   ```

   The jar will be generated in build/libs.

2. Create a new "**corpus**" directory (the name must be spelled exactly like this and in lowercase). Add the Alloy models you would like to profile do the "corpus" folder (models can be placed in subdirectories). Place the ProfilerCharacs.java class,  the generated profiler-characs.jar file (located in build/libs) and the "corpus" folder in the same directory on your local machine. 

3. Open a terminal window/command prompt/PowerShell and compile the ProfilerCharacs.java file using the following command:

     ~~~shell
     javac -cp .\profiler-characs.jar ProfilerCharacs.java
     ~~~

     The command will generate a ProfilerCharacs.class file in the same directory if executed successfully.

  4. Run the profiling script of your choice using the following command:

     ~~~shell
     java -cp .\profiler-characs.jar com.alloyprofiling.characteristics.ProfilerCharacs script
     ~~~

  Replace ```script``` with one of the following command line arguments:

  * Length:
    * ```length```: Runs the "ModelLength.java" script. Produces a "length.txt" file containing the number of non-blank line in each model. "length.txt" is located in Results/CharacteristicsOfModels/Length/ 
  * Signatures:
    * ```sigs```: Runs the "Signatures.java" script. Produces a "sigs.txt" file containing the total number of signatures in each model. "sigs.txt" is located in Results/CharacteristicsOfModels/Sets/ 
    * ```toplevel```: Runs the "TopLevelSigs.java" script. Produces a "topSigs.txt" file containing the number of top-level signatures in each model. "topSigs.txt" is located in Results/CharacteristicsOfModels/Sets/ 
    * ```subsets```: Runs the "SubsetSigs.java" script. Produces a "subSigs.txt" file containing the number of subset signatures in each model. "subSigs.txt" is located in Results/CharacteristicsOfModels/Sets/ 
    * ```extensions```: Runs the "ExtensionSigs.java" script. Produces a "extSigs.txt" file containing the number of subsignature extensions in each model. "topSigs.txt" is located in Results/CharacteristicsOfModels/Sets/ 
    * ```abstract```: Runs the "AbstractSigs.java" script. Produces a "absSigs.txt" file containing the number of abstract signatures in each model. "absSigs.txt" is located in Results/CharacteristicsOfModels/Sets/ 
    * ```scalars```: Runs the "Scalars.java" script. Produces the "oneSigs.txt" and "enums.txt" files containing the number of one sigs and enum (extensions) in each model respectively. "oneSigs.txt"  and "enums.txt" are located in Results/CharacteristicsOfModels/Sets/ 
    * ```fields```: Runs the "Fields.java" script. Produces a "fields.txt" file containing the number of fields per signature in each model. "fields.txt" is located in Results/CharacteristicsOfModels/Sets/ 
  * Constraint Holders:
      * ```formulas```: Runs the "FormulasExpanded.java" script. Produces a "formulaCount_expanded.txt.txt" file containing the number of fact blocks in each model. "formulaCount_expanded.txt.txt" is located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```predicates```: Runs the "Predicates.java" script. Produces the "predicateDecls.txt", "predicateUsesCmds.txt" and "predicateUsesExprs.txt" files containing the number of predicate declarations and predicate uses (in commands and formulas) in each model respectively. "predicateDecls.txt", "predicateUsesCmds.txt" and "predicateUsesExprs.txt" are located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```functions```: Runs the "Functions.java" script. Produces the "functionDecls.txt", "functionUsesCmds.txt" and "functionUsesExprs.txt" files containing the number of function declarations and function uses (in commands and formulas) in each model respectively. "functionDecls.txt", "functionUsesCmds.txt" and "functionUsesExprs.txt" are located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```fact```: Runs the "Facts.java" script. Produces a "facts.txt" file containing the number of fact blocks in each model. "facts.txt" is located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```sigfact```: Runs the "SigFacts.java" script. Produces a "sigFacts.txt" file containing the number of signatures with fact blocks in each model. "sigFacts.txt" is located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```runcheck```: Runs the "RunCheck.java" script. Produces the "run.txt" and "check.txt" files containing the number of run queries and check queries in each model respectively. "predicateDecls.txt" and "predicateUses" are located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```runforms```: Runs the "RunForms.java" script. Produces the "runName.txt", "runBlock.txt" and "runNamedBlcok.txt " files containing the number of run commands used with names, blocks and named blocks in each model respectively. The result files are located in Results/CharacteristicsOfModels/ConstraintHolders/ 
    * ```checkforms```: Runs the "CheckForms.java" script. Produces the "checkName.txt", "checkBlock.txt" and "checkNamedBlcok.txt " files containing the number of run commands used with names, blocks and named blocks in each model respectively. The result files are located in Results/CharacteristicsOfModels/ConstraintHolders/ 
  * Corner Cases:
      * ```unionsuperset```: Runs the "UnionSuperset.java" script. Produces a "unionSuperset.txt" file containing the number of union supersets in each model. "unionSuperset.txt" is located in Results/CharacteristicsOfModels/CornerCases/ 
    * ```bitshifting```: Runs the "BitshiftingOperators.java" script. Produces a "bitshift.txt" file containing the number of bit shifting operators in each model. "bitshift.txt" is located in Results/CharacteristicsOfModels/CornerCases/ 
    * ```constants```: Runs the "Constants.java" script. Produces the "none.txt", "univ.txt" and "iden.txt" files containing the number of none, univ and iden constants in each model respectively. "none.txt", "univ.txt" and "iden.txt" are located in Results/CharacteristicsOfModels/CornerCases/ 
    * ```macros```: Runs the "Macros.java" script. Produces a "macros.txt" file containing the number of macros in each model. The script also prints the number of models containing macros. "macros.txt" is located in Results/CharacteristicsOfModels/CornerCases/ 
    * ```reluniondiff```: Runs the "RelUnionDiff.java" script. Produces the "relUnion.txt" and "relDiff.txt" files containing the number of fields declared using set union and set difference in each model respectively. "relUnino.txt" and "relDiff.txt" are located in Results/CharacteristicsOfModels/CornerCases/ 
* Linear:
  * ```lengthsetsformulas```: Runs the "LengthSetsFormulas.java" script. Produces a "lengthSetsExprs.csv" file containing the length, number of sets and number of formulas in each model. "lengthSetsExprs.csv " is located in Results/CharacteristicsOfModels/Linear/ 
  * ```fieldstop```: Runs the "FieldsTopSigs.java" script. Produces a "fieldstop.csv" file containing the number of top-level sigs and number of fields in each model. "fieldstop.csv " is located in Results/CharacteristicsOfModels/Linear/ 
  * ```fieldsext```: Runs the "FieldsExt.java" script. Produces a "fieldsext.csv" file containing the length, number of sets and number of formulas in each model. "fieldsext.csv " is located in Results/CharacteristicsOfModels/Linear/ 

  **Example**

  Running the "Signatures.java" script using the ```sigs``` command line argument:

  ~~~shell
  java -cp .\profiler-characs.jar com.alloyprofiling.characteristics.ProfilerCharacs sigs
  ~~~


## Patterns of Use

These scripts are located in src/main/java/com/alloyprofiling/patternsofuse

These scripts are split into 5 sub-categories:

  * Modules
  * Integers
  * Sets
  * Formulas
  * Scopes

All these scripts can be run using the ProfilerPatterns.java class located under src/main/java/com/alloyprofiling/patternsofuse.

### To run a script:

1. Generate profiler-patterns.jar using the following **gradle** command if you have gradle installed on your local machine:

   ```shell
   gradle patternsJar
   ```

   If you don't have gradle installed, you can use **gradlew**:

   **Linux**:

   ```shell
   ./gradlew patternsJar
   ```

   **Windows:**

   ```shell
   .\gradlew.bat patternsJar
   ```

   The jar will be generated in build/libs.

2. Create a new "**corpus**" directory (the name must be spelled exactly like this and in lowercase). Place the ProfilerPatterns.java class,  the generated profiler-patterns.jar file (located in build/libs) and the "corpus" folder in the same directory on your local machine.  Add the Alloy models you would like to profile do the "corpus" folder (models can be placed in subdirectories). 

3. Open a terminal window/command prompt/PowerShell and compile the ProfilerCharacs.java file using the following command:

   ~~~shell
   javac -cp .\profiler-patterns.jar ProfilerPatterns.java
   ~~~

   The command will generate a ProfilerPatterns.class file in the same directory if executed successfully.

  4. Run the profiling script of your choice using the following command:

     ~~~shell
     java -cp .\profiler-patterns.jar com.alloyprofiling.patternsofuse.ProfilerPatterns script
     ~~~

  Replace ```script``` with one of the following command line arguments:

* Modules:

  * ```integerlibrary```: Runs the "IntegerLibrary.java" script. Produces a "integer_mod.txt" and "integer_noMod.txt" files containing the number of models that import the integer module and use integers without importing the integer module respectively. "integer_mod.txt" and "integer_noMod.txt" are located in Results/PatternsOfUse/Modules/ 
  * ```moduleuse```: Runs the "ModuleUse.java" script. Produces a 12 files containing the number of open statements for each library module and the number of open statements used with user-created modules. The result files are located in Results/PatternsOfUse/Modules/ 
  * ```moduleusemodels```: Runs the "ModuleUseModels.java" script. Produces a 12 files containing the number of models that contain open statements for each library module and the number of models containing open statements used with user-created modules. The result files are located in Results/PatternsOfUse/Modules/ 

* Integers:

  * ```integeruse```: Runs the "IntegerUse.java" script. Produces a "intConsts.txt" and "intFields.txt" files containing the number of integer constants and integer fields respectively. "intConsts.txt" and "intFields.txt" are located in Results/PatternsOfUse/Integers/ 
  * ```integerordering```: Runs the "IntegerOrdering.java" script. Produces a "intOrdering.txt" and "intNoOrdering.txt" files containing the number of integer fields that can be turned into an application of the ordering module and the number of integer fields that cannot be turned into an ordering in each model respectively. "intOrdering.txt" and "intNoOrdering.txt" are located in Results/PatternsOfUse/Integers/ 
  * ```setcardequalconst```: Runs the "SetCardEqualConst.java" script. Produces 4 .txt files containing the number of different uses of the set cardinality operator with integers and formulas (see RQ #23 in thesis for more details). The result files are located in Results/PatternsOfUse/Integers/

* Sets:

  * ```sethierarchytrees```: Runs the "SetHierarchyTrees.java" script. Produces "extDepth.txt", "extWidth.txt" and "subDepth.txt" files containing the depth of the extension hierarchy tree, width of the extension hierarchy tree and depth of the subset hierarchy tree for each model in the corpus. The result files are located in Results/PatternsOfUse/Sets/
  * ```scg```: Runs the "SCG.java" script. Produces "SCG.txt" containing the SCG metric value for each model in the corpus. "SCG.txt" is located in Results/PatternsOfUse/Sets/
  * ```quantification```: Runs the "Quantification.java" script. Produces 4 .txt files containing the number of top, extension, subset and set level quantification counts in each model. The result files are located in Results/PatternsOfUse/Sets/
  * ```sigsasstructures```:  Runs the "SigsAsStructures.java" script. Produces "structures.txt" containing the number of signatures used as structures (records) in each model in the corpus. "structures.txt" is located in Results/PatternsOfUse/Sets/
  * ```abssigsnochildren```:  Runs the "AbsSigsNoChildren.java" script. Produces "absSigsNoChild.txt" containing the number of abstract signatures without children in each model in the corpus. "absSigsNoChild.txt" is located in Results/PatternsOfUse/Sets/
  * ```abssigsnofields```:  Runs the "AbsSigsNoFields.java" script. Produces "absFields.txt" containing the number of fields under abstract signatures in each model in the corpus. The scripts also prints the number of abstract signatures with and without fields as well as the total number of abstract signatures. "absFields.txt" is located in Results/PatternsOfUse/Sets/

* Formulas:

  * ```formulastyles```: Runs the "FormulaStyles.java" script. Produces 6 .txt files containing the number of models that fall in each modeling category (see RQ# 30 in thesis for more details) as well as 3 .txt containing the number of formulas that fall in each modeling style category in each model. The result files are located in Results/PatternsOfUse/Formulas/

* Scopes:

  * ```scopes```: Runs the "Scopes.java" script. Produces 6 .txt files containing the number of commands that fall in each scope category (see RQ# 31 in thesis for more details). The result files are located in Results/PatternsOfUse/Scopes/
  * ```scopelevels```: Runs the "ScopeLevels.java" script. Produces 15 .txt files containing the number of scopes set at the command, signature and overall levels (see RQ # 32 for more details). The result files are located in Results/PatternsOfUse/Scopes/
  * ```orderingnonexact```: Runs the "OrderingNonExact.java" script. Produces 4 .txt files containing the number of ordered sets with non-exact scopes (across all 3 non-exact categories and total). he result files are located in Results/PatternsOfUse/Scopes/
  * ```integerscopes```: Runs the "IntegerScopes.java" script. Produces "intScopes.txt" and "intScopeNums.txt" files containing the number of integers scopes in each model and the integer scope value respectively. "intScopes.txt" and "intScopeNums.txt" are located in located in Results/PatternsOfUse/Scopes/

  **Example**

  Running the "IntegerLibrary.java" script using the ```integerlibrary``` command line argument:

   ~~~shell
   java -cp .\profiler-patterns.jar com.alloyprofiling.patternsofuse.ProfilerPatterns integerlibrary
   ~~~

## Analysis Complexity 

These scripts are located in src/main/java/com/alloyprofiling/analysiscomplexity

These scripts are split into 4 sub-categories:

  * Second-order Operators
  * Partial and Total Functions
  * Depth of Joins and Quantification
  * Field Arity

All these scripts can be run using the ProfilerPatterns.java class located under src/main/java/com/alloyprofiling/analysiscomplexity.

### To run a script:

1. Generate profiler-complexity.jar using the following **gradle** command if you have gradle installed on your local machine:

   ```shell
   gradle complexityJar
   ```

   If you don't have gradle installed, you can use **gradlew**:

   **Linux**:

   ```shell
   ./gradlew complexityJar
   ```

   **Windows:**

   ```shell
   .\gradlew.bat complexityJar
   ```

   The jar will be generated in build/libs.

2. Create a new "**corpus**" directory (the name must be spelled exactly like this and in lowercase). Place the ProfilerComplexity.java class,  the generated profiler-complexity.jar file (located in build/libs) and the "corpus" folder in the same directory on your local machine.  Add the Alloy models you would like to profile do the "corpus" folder (models can be placed in subdirectories). 

3. Open a terminal window/command prompt/PowerShell and compile the ProfilerCharacs.java file using the following command:

   ~~~shell
   javac -cp .\profiler-complexity.jar ProfilerComplexity.java
   ~~~

   The command will generate a ProfilerComplexity.class file in the same directory if executed successfully.

  4. Run the profiling script of your choice using the following command:

     ~~~shell
     java -cp .\profiler-complexity.jar com.alloyprofiling.analysiscomplexity.ProfilerComplexity script
     ~~~

  Replace ```script``` with one of the following command line arguments:

* Second-order Operators:
  * ```setcardinality```: Runs the "SetCardinality.java" script. Produces "setCard.txt" file containing the number of set cardinality operators in each model. The script also prints the number of models that contain at least one set cardinality operator. "setCard.txt" is located in Results/AnalysisComplexity/SecondOrderOperators/
  * ```transitiveclosure```: Runs the "TCOperators.java" script. Produces "tcOp.txt", "reftc.txt" and "nonreftc.txt" containing the number of transitive closure operators (reflexive and non-reflexive), the number of reflexive transitive closure operators and the number of non-reflexive transitive closure operators in each model respectively. The result files are located in Results/AnalysisComplexity/SecondOrderOperators/
* Partial and Total Functions:
  * ```partialfuncs```: Runs the "PartialFuncs.java" script. Produces the "partialFuncs.txt" file containing the number of user-created partial functions in each model. "partialFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
  * ```totalfuncs```: Runs the "TotalFuncs.java" script. Produces the "totalFuncs.txt" file containing the number of user-created total functions in each model. "totalFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
  * ```partialfuncsutil```: Runs the "PartialFuncs_util.java" script.  Produces the "utilPartialFuncs.txt" file containing the number of utility partial functions in each model. "utilPartialFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
  * ```totalfuncsutil```: Runs the "TotalFuncs_util.java" script.  Produces the "utilTotalFuncs.txt" file containing the number of utility total functions in each model. "utilTotalFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
  * ```overallpartial```: Runs the "OverallPartialFuncs.java" script. Produces the "overallPartialFuncs.txt" file containing the total number (user-created and utility) partial functions in each model. "overallPartialFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
  * ```overalltotal```l: Runs the "OverallTotalFuncs.java" script. Produces the "overallTotalFuncs.txt" file containing the total number (user-created and utility) total functions in each model. "overallPartialFuncs.txt" is located in located in Results/AnalysisComplexity/PartialTotalFunctions/
* Depth of Joins and Quantification:
  * ```depthjoin```: Runs the "DepthJoin.java" script. Produces "DOJ.txt", "dotJoin.txt" and "boxJoin.txt" containing the depths of joins across all models, the number of dot joins in each model and the number of box joins in each model respectively. The result files are located in Results/AnalysisComplexity/DepthJoinQuantification/
  * ```depthquantification```: Runs the "DepthQuantification.java" script. Produces the "DOQ.txt" file containing the depths of quantification across all models. "DOQ.txt" is located in Results/AnalysisComplexity/DepthJoinQuantification/
* Field Arity:
  * ```fieldarity```: Runs the "FieldArity.java" script. Produces the "arity.txt" file containing the arity of all fields across all models. "arity.txt" is located in Results/AnalysisComplexity/FieldArity/

**Example**

Running the "SetCardinality.java" script using the ```setcardinality``` command line argument:

 ~~~shell
 java -cp .\profiler-complexity.jar com.alloyprofiling.analysiscomplexity.ProfilerComplexity setcardinality
 ~~~

**Note**: the "unused" package contains unused scripts that may or may not be complete/operational. 

## Expert Models

The expert models used in this study were drawn from the following sources:

* chord-pamela-zave: 
	
  Zave, Pamela. “Reasoning About Identifier Spaces: How to Make Chord Correct.” IEEE transactions on software engineering 43.12 (2017): 1144–1156. Web.

* 2scxlb3tbo5bmvmwplglqils7a5uarmx-talks: Streamlined Causal Consistency (SCC) Model (NVIDIA Copyright)
* 3zltn65gds66b6f4q3lvbtgdkb6snmuu-alloy: 

  Baugh J, Altuntas A. Formal methods and finite element analysis of hurricane storm surge: A case study in software verification. Science of computer programming. 2018;158:100-121. doi:10.1016/j.scico.2017.08.012

* 7d25ioxqmue65lp6ntzz735gpbg4fmgq-amazon-snapshot-spec: Cahill M, Röhm U, Fekete A. Serializable isolation for snapshot databases. ACM transactions on database systems. 2009;34(4):1-42. doi:10.1145/1620585.1620587

* 7z32luflamhdcixvt6nwznnud4oi6dbr-MSV: 
	* lc-lenses.als:
	Macedo, Nuno & Pacheco, Hugo & Cunha, Alcino & Oliveira, José. (2013). Composing Least-change Lenses. Electronic Communications of the EASST. 57. 10.14279/tuj.eceasst.57.868. 
	* ertms_1A.als: Hoang, Thai Son, Michael Butler, and Klaus Reichl. “The Hybrid ERTMS/ETCS Level 3 Case Study.” Abstract State Machines, Alloy, B, TLA, VDM, and Z. Cham: Springer International Publishing, 2018. 251–261. Web.
	* etl_scd.als, fm2cfs.als: Oliveira, Bruno, Orlando Belo, and Nuno Macedo. “Towards a Formal Validation of ETL Patterns Behaviour.” Model and Data Engineering. Cham: Springer International Publishing, 2016. 156–165. Web.
	* OLAPUsagePrefs.als: R. Carvalho, A. Cunha, N. Macedo and A. Santos, "Verification of system-wide safety properties of ROS applications," 2020 IEEE/RSJ International Conference on Intelligent Robots and Systems (IROS), 2020, pp. 7249-7254, doi: 10.1109/IROS45743.2020.9341085.
	* farmer_pt.als, HotelLocking: Nuno Macedo
	Assistant Professor @ FEUP & INESC TEC https://nmacedo.github.io/
	* CD2DBS_keys: Macedo, Nuno, Tiago Guimarães, and Alcino Cunha. “Model Repair and Transformation with Echo.” Proceedings of the 28th IEEE/ACM International Conference on Automated Software Engineering. IEEE Press, 2013. 694–697. Web.
	* CD2BDS_simple: Macedo, Nuno, and Alcino Cunha. “Implementing QVT-R Bidirectional Model Transformations Using Alloy.” Fundamental Approaches to Software Engineering. Berlin, Heidelberg: Springer Berlin Heidelberg. 297–311. Web.
	* ElevatorSPL: Classen, Andreas et al. “Symbolic Model Checking of Software Product Lines.” Proceedings of the 33rd International Conference on Software Engineering. ACM, 2011. 321–330. Web.
	* NHSM.als: Macedo, Nuno, and Alcino Cunha. “Least-Change Bidirectional Model Transformation with QVT-R and ATL.” Software and systems modeling 15.3 (2014): 783–810. Web.
*  lkicptlz3eklrbu7ppmltlkebwrvzhdq-zigbee-alloy-svn-to-git: ZigBee/IEEE 802.15.4 stack of network protocols written by Andrey Kupriyanov https://www.react.uni-saarland.de/people/kupriyanov.html
* oujlbmnutprdhddstyudppn7t35n43os-CANBus/CANBus.als:17: https://freesoft.dev/program/122067057 
* 3zltn65gds66b6f4q3lvbtgdkb6snmuu-alloy:
  Baugh, John, and Alper Altuntas. “Formal Methods and Finite Element Analysis of Hurricane Storm Surge: A Case Study in Software Verification.” Science of computer programming 158 (2018): 100–121. Web.
* 5x4l2fj5nfbq3cz2dumwdt57g3kig3rd-litmustestgen: NVIDIA Copyrighted models https://github.com/NVlabs/litmustestgen
* gumxtrzzbkrtwi7jtwyu7eibi3fwhgmf-models:
   * algorithms/discovery: Models an Intentional Naming System (INS), a scheme for dynamic resource discovery in a dynamic environment. MIT Computer Science & Artificial Intelligence Laboratory. Sarfraz Khurshid, http://sdg.lcs.mit.edu/pubs/2000/INS_ASE00.pdf
   * algorithms/distributed-hashtable: Models the chord distributed hash table lookup protocol. MIT Computer Science & Artificial Intelligence Laboratory. http://www.pdos.lcs.mit.edu/papers/chord:sigcomm01/ 
   * algorithms/gc: Model of mark and sweep garbage collection.MIT Computer Science & Artificial Intelligence Laboratory.
   * algorithms/mapping: Model of views in object-oriented programming. MIT Computer Science & Artificial Intelligence Laboratory. Written by Daniel Jackson.
   * algorithms/multicasting: Taghdiri, M., & Jackson, D. (2003). A Lightweight Formal Analysis of a Multicast Key Management Scheme. FORTE.
   * algorithms/mutex:
       * dijkstra-2-process.als: E. W. Dijkstra, Cooperating sequential processes. Technological University, Eindhoven, The Netherlands, September 1965.Reprinted in Programming Languages, F. Genuys, Ed., Academic Press, New York, 1968, 43-112.
       * dijkstra-K-state.als: E.W.Dijkstra, "Self-Stabilizing Systems in Spite of  Distributed Control", Comm. ACM, vol. 17, no. 11, pp. 643-644, Nov. 1974
       * peterson.als: Model of Peterson's algorithm for mutual exclusion of n processes. The names kept similar to Murphi specification to make correspondence clear. MIT Computer Science & Artificial Intelligence Laboratory
   * algorithms/synchronisation: Model of a generic file synchronizer. Written by Tina Nolte.
http://sdg.lcs.mit.edu/pubs/theses/tnolte_masters.pdf 
   * ietf-rfcs: The 'Basic' HTTP Authentication Scheme. https://tools.ietf.org/html/rfc7617
   * models/java/java-maps.als: A partial formal definition of a Map in Java. Peter Kriens. http://aqute.biz/2017/07/15/Alloy.html
   * models/java/java-types.als, models/java/javatypes-soundness.als:
     Model of the Java type system written by Daniel Jackson.
   * models/microsoft-com: Model of Microsoft Component Object Model (COM) query interface and aggregation mechanism. Written by Daniel Jackson. http://sdg.lcs.mit.edu/~dnj/publications/com-fse00.pdf
   * models/railway: A simple model of a railway system written by Danile Jackson.
   * paper-examples: Jackson, Daniel. “Alloy: a Language and Tool for Exploring Software Designs.” Communications of the ACM 62.9 (2019): 66–76. Web.
   * puzzles, utilities, simple models, logic: CSAIL MIT. https://www.csail.mit.edu/







 
 






​	

