package com.alloyprofiling.analysiscomplexity;

import com.alloyprofiling.analysiscomplexity.arity.FieldArity;
import com.alloyprofiling.analysiscomplexity.depthjoinsquant.DepthJoin;
import com.alloyprofiling.analysiscomplexity.depthjoinsquant.DepthQuantification;
import com.alloyprofiling.analysiscomplexity.partialtotalfuncs.*;
import com.alloyprofiling.analysiscomplexity.secondorderoperators.SetCardinality;
import com.alloyprofiling.analysiscomplexity.secondorderoperators.TCOperators;

public class ProfilerComplexity {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument given -- consult README file");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "setcardinality":
                SetCardinality.main(null);
                break;
            case "transitiveclosure":
                TCOperators.main(null);
                break;
            case "partialfuncs":
                PartialFuncs.main(null);
                break;
            case "totalfuncs":
                TotalFuncs.main(null);
                break;
            case "partialfuncsutil":
                PartialFuncs_util.main(null);
                break;
            case "totalfuncsutil":
                TotalFuncs_util.main(null);
                break;
            case "overallpartial":
                OverallPartialFuncs.main(null);
                break;
            case "overalltotal":
                OverallTotalFuncs.main(null);
                break;
            case "depthjoin":
                DepthJoin.main(null);
                break;
            case "depthquantification":
                DepthQuantification.main(null);
                break;
            case "fieldarity":
                FieldArity.main(null);
                break;
            default:
                System.out.println("Invalid argument -- consult README file");
        }
    }
}
