package com.alloyprofiling.patternsofuse;

import com.alloyprofiling.patternsofuse.formulas.FormulaStyles;
import com.alloyprofiling.patternsofuse.integers.IntegerOrdering;
import com.alloyprofiling.patternsofuse.integers.IntegerUse;
import com.alloyprofiling.patternsofuse.integers.SetCardEqualConst;
import com.alloyprofiling.patternsofuse.modules.IntegerLibrary;
import com.alloyprofiling.patternsofuse.modules.ModuleUse;
import com.alloyprofiling.patternsofuse.modules.ModuleUseModels;
import com.alloyprofiling.patternsofuse.scopes.IntegerScopes;
import com.alloyprofiling.patternsofuse.scopes.OrderingNonExact;
import com.alloyprofiling.patternsofuse.scopes.ScopeLevels;
import com.alloyprofiling.patternsofuse.scopes.Scopes;
import com.alloyprofiling.patternsofuse.sets.*;

public class ProfilerPatterns {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument given -- consult README file");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "integerlibrary":
                IntegerLibrary.main(null);
                break;
            case "moduleuse":
                ModuleUse.main(null);
                break;
            case "moduleusemodels":
                ModuleUseModels.main(null);
                break;
            case "integeruse":
                IntegerUse.main(null);
                break;
            case "integerordering":
                IntegerOrdering.main(null);
                break;
            case "setcardequalconst":
                SetCardEqualConst.main(null);
                break;
            case "sethierarchytrees":
                SetHierarchyTrees.main(null);
                break;
            case "scg":
                SCG.main(null);
                break;
            case "quantification":
                Quantification.main(null);
                break;
            case "sigsasstructures":
                SigsAsStructures.main(null);
                break;
            case "abssigsnochildren":
                AbstractSigsNoChildren.main(null);
                break;
            case "abssigsnofields":
                AbstractSigsNoFields.main(null);
                break;
            case "formulasstyles":
                FormulaStyles.main(null);
                break;
            case "scopes":
                Scopes.main(null);
                break;
            case "scopelevels":
                ScopeLevels.main(null);
                break;
            case "orderingnonexact":
                OrderingNonExact.main(null);
                break;
            case "integerscopes":
                IntegerScopes.main(null);
                break;
            default:
                System.out.println("Invalid argument -- consult README file");
        }
    }
}
