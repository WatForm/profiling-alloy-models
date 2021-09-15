package com.alloyprofiling.characteristics;

import com.alloyprofiling.characteristics.constraints.*;
import com.alloyprofiling.characteristics.cornercases.*;
import com.alloyprofiling.characteristics.length.ModelLength;
import com.alloyprofiling.characteristics.linear.FieldsExt;
import com.alloyprofiling.characteristics.linear.FieldsTopSigs;
import com.alloyprofiling.characteristics.linear.LengthSetsFormulas;
import com.alloyprofiling.characteristics.signatures.*;

public class ProfilerCharacs {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument given -- consult README file");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "length":
                ModelLength.main(null);
                break;
            case "sigs":
                Signatures.main(null);
                break;
            case "toplevel":
                TopLevelSigs.main(null);
                break;
            case "extensions":
                ExtensionSigs.main(null);
                break;
            case "subsets":
                SubsetSigs.main(null);
                break;
            case "abstract":
                AbstractSigs.main(null);
                break;
            case "scalars":
                Scalars.main(null);
                break;
            case "fields":
                Fields.main(null);
                break;
            case "formulas":
                FormulasExpanded.main(null);
                break;
            case "facts":
                Facts.main(null);
                break;
            case "sigfacts":
                SigFacts.main(null);
                break;
            case "predicates":
                Predicates.main(null);
                break;
            case "functions":
                Functions.main(null);
                break;
            case "assertions":
                Assertions.main(null);
                break;
            case "runcheck":
                RunCheck.main(null);
                break;
            case "runforms":
                RunForms.main(null);
                break;
            case "checkforms":
                CheckForms.main(null);
                break;
            case "unionsuperset":
                UnionSuperset.main(null);
                break;
            case "bitshifting":
                BitshiftingOperators.main(null);
                break;
            case "constants":
                Constants.main(null);
                break;
            case "macros":
                Macros.main(null);
                break;
            case "reluniondiff":
                RelUnionDiff.main(null);
                break;
            case "lengthsetsformulas":
                LengthSetsFormulas.main(null);
                break;
            case "fieldstopsigs":
                FieldsTopSigs.main(null);
                break;
            case "fieldsext":
                FieldsExt.main(null);
                break;
            default:
                System.out.println("Invalid argument -- consult README file");
        }
    }
}
