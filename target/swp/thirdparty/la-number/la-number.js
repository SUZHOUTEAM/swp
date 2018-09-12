/*!
 * LaNumber v1.0
 * https://github.com/.../lanumber
 *
 * Copyright (c) 2017-2018 Lamando
 * Released under the MIT license
 *
 * Date: 2017-08-30T12:11:36.477Z
 * 
 * Description: DataTables Post-packaging  
 */
(function ($) {
	
	$.LaNumber = function (){ };
	
	$.LaNumber.prototype = {
		
		/**
		 * <h1>Function description：<h1>
		 * &nbsp; 
		 * @param num
		 * @return 
		 * 		Removes 0 of the invalid num
		 */
		floatFormatZero: function (num){
			if(typeof num === 'undefined') {
				throw new Error("Paramer is undefined");
			}
			
			if(isNaN(num)) { // Paramer can be numeric or digital strings
				throw new Error("Paramer is NaN");
			}
			
			if(typeof num !== 'string') {
				num = num + '';
			}
			
			var pointIndex;
			if((pointIndex = num.indexOf("."))<0){
				return Number(num);
			}
			
			var i = num.length; 
			for (; i>num.pointIndex; --i) {
				if(num.substring(i-2, i-1)) {
					break;
				}
			}
			
			return Number(num.substring(0, i));
		},
		
		/**
		 * <h1>Function description：<h1>
		 * &nbsp; additive
		 * @param addend
		 * @param augend
		 * @return 
		 * 		addend + augend
		 */
		add: function (addend, augend) {
		    var c, d, e;
		    
		    try {
		        c = addend.toString().split(".")[1].length;
		    } catch (f) {
		        c = 0;
		    }
		    
		    try {
		        d = augend.toString().split(".")[1].length;
		    } catch (f) {
		        d = 0;
		    }
		    
		    e = Math.pow(10, Math.max(c, d));
		    
		    return (this.mul(addend, e) + this.mul(augend, e)) / e;
		    
		    // return e = Math.pow(10, Math.max(c, d)), 
		    //		(this.mul(addend, e) + this.mul(augend, e)) / sum;
		},

		/**
		 * <h1>Function description：<h1>
		 * &nbsp; subtraction
		 * @param subtrahend
		 * @param minuend
		 * @return 
		 * 		subtrahend - minuend
		 */
		sub: function (subtrahend, minuend) {
		    var c, d, e;
		    
		    try {
		        c = subtrahend.toString().split(".")[1].length;
		    } catch (f) {
		        c = 0;
		    }
		    
		    try {
		        d = minuend.toString().split(".")[1].length;
		    } catch (f) {
		        d = 0;
		    }
		    
		    debugger;
		    e = Math.pow(10, Math.max(c, d));
		    
		    return (this.mul(subtrahend, e) - this.mul(minuend, e)) / e;
		},
		
		/**
		 * <h1>Function description：<h1>
		 * &nbsp; multiplication
		 * @param multiplier
		 * @param multiplicand
		 * @return 
		 * 		multiplier * multiplicand
		 */
		mul: function (multiplier, multiplicand) {
		    var c = 0, d = multiplier.toString(), e = multiplicand.toString();
		    
		    try {
		        c += d.split(".")[1].length;
		    } catch (f) {}
		    
		    try {
		        c += e.split(".")[1].length;
		    } catch (f) {}
		    
		    return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
		},
		
		/**
		 * <h1>Function description：<h1>
		 * &nbsp; division
		 * @param divisor
		 * @param dividend
		 * @return 
		 * 		divisor / dividend
		 */
		div: function (divisor, dividend) {
		    var c, d, e = 0, f = 0;
		    
		    try {
		        e = divisor.toString().split(".")[1].length;
		    } catch (g) {}
		    
		    try {
		        f = dividend.toString().split(".")[1].length;
		    } catch (g) {}
		    
		    c = Number(divisor.toString().replace(".", ""));
		    d = Number(dividend.toString().replace(".", ""));
		    
		    return this.mul(c / d, Math.pow(10, f - e));
		}
        
	};
})(jQuery);