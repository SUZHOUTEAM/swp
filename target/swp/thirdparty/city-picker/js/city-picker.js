/*!
 * CityPicker v1.0.2
 * https://github.com/tshi0912/citypicker
 *
 * Copyright (c) 2015-2016 Tao Shi
 * Released under the MIT license
 *
 * Date: 2016-02-29T12:11:36.477Z
 */

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define(['jquery', 'ChineseDistricts'], factory);
    } else if (typeof exports === 'object') {
        // Node / CommonJS
        factory(require('jquery'), require('ChineseDistricts'));
    } else {
        // Browser globals.
        factory(jQuery, ChineseDistricts);
    }
})(function ($, ChineseDistricts) {

    'use strict';
    var digitReg = /(^[0-9]+$)/;// 判断区域代码是不是数字  zhugl 修改于2016/11/01
    if (typeof ChineseDistricts === 'undefined') {
        throw new Error('The file "city-picker.data.js" must be included first!');
    }

    var NAMESPACE = 'citypicker';
    var EVENT_CHANGE = 'change.' + NAMESPACE;
    var PROVINCE = 'province';
    var CITY = 'city';
    var DISTRICT = 'district';

    function CityPicker(element, options) {
        this.$element = $(element);
        this.$dropdown = null;
        this.options = $.extend({}, CityPicker.DEFAULTS, $.isPlainObject(options) && options);
        this.active = false;
        this.dems = [];
        this.needBlur = false;
        this.init();
    }

    CityPicker.prototype = {
        constructor: CityPicker,

        init: function () {

            this.defineDems();

            this.render();

            this.bind();

            this.active = true;
        },

        render: function () {
            var p = this.getPosition(),
                placeholder = this.$element.attr('placeholder') || this.options.placeholder,
                textspan = '<span class="city-picker-span" style="' +
                    this.getWidthStyle(p.width) + 'height:' +
                    p.height + 'px;line-height:' + (p.height - 1) + 'px;">' +
                    (placeholder ? '<span class="placeholder">' + placeholder + '</span>' : '') +
                    '<span class="title"></span><div class="arrow"></div>' + '</span>',

                dropdown = '<div class="city-picker-dropdown" style="left:0px;top:100%;' +
                    this.getWidthStyle(p.width, true) + '">' +
                    '<div class="city-select-wrap">' +
                    '<div class="city-select-tab">' +
                    '<a class="active" data-count="province">省份</a>' +
                    (this.includeDem('city') ? '<a data-count="city">城市</a>' : '') +
                    (this.includeDem('district') ? '<a data-count="district">区县</a>' : '') + '</div>' +
                    '<div class="city-select-content">' +
                    '<div class="city-select province" data-count="province"></div>' +
                    (this.includeDem('city') ? '<div class="city-select city" data-count="city"></div>' : '') +
                    (this.includeDem('district') ? '<div class="city-select district" data-count="district"></div>' : '') +
                    '</div></div>';

            this.$element.addClass('city-picker-input');
            this.$textspan = $(textspan).insertAfter(this.$element);
            this.$dropdown = $(dropdown).insertAfter(this.$textspan);
            var $select = this.$dropdown.find('.city-select');
            
            // setup this.$province, this.$city and/or this.$district object
            $.each(this.dems, $.proxy(function (i, type) {
                this['$' + type] = $select.filter('.' + type + '');
            }, this));

            this.refresh();
        },

        refresh: function (force) {
            // clean the data-item for each $select
            var $select = this.$dropdown.find('.city-select');
            $select.data('item', null);
            // parse value from value of the target $element
            var val = this.$element.val() || '';
            if (digitReg.test(val)) {
                var arrayObj = new Array(3); //创建一个数组
                if (val.length >= 2) {
                    arrayObj[0] = val.substring(0,2)+"0000";
                }
                if (val.length >= 4) {
                    arrayObj[1] = val.substring(0,4)+"00";
                }
                if (val.length >= 6) {
                    arrayObj[2] = val.substring(0,6);
                }
                
                $.each(this.dems, $.proxy(function (i, type) {
                    if (arrayObj[i] && i < arrayObj.length) {
                        this.options[type] = arrayObj[i];
                    } else if (force) {
                        this.options[type] = '';
                    }
                    this.output(type);
                }, this));
            } else {
                val = val.split('/');
                $.each(this.dems, $.proxy(function (i, type) {
                    if (val[i] && i < val.length) {
                        this.options[type] = val[i];
                    } else if (force) {
                        this.options[type] = '';
                    }
                    this.output(type);
                }, this));
            }
            
            this.tab(PROVINCE);
            this.feedText();
            this.feedVal();
        },

        defineDems: function () {
            var stop = false;
            $.each([PROVINCE, CITY, DISTRICT], $.proxy(function (i, type) {
                if (!stop) {
                    this.dems.push(type);
                }
                if (type === this.options.level) {
                    stop = true;
                }
            }, this));
        },

        includeDem: function (type) {
            return $.inArray(type, this.dems) !== -1;
        },

        getPosition: function () {
            var p, h, w, s, pw;
            p = this.$element.position();
            s = this.getSize(this.$element);
            h = s.height;
            w = s.width;
            if (this.options.responsive) {
                pw = this.$element.offsetParent().width();
                if (pw) {
                    w = w / pw;
                    if (w > 0.99) {
                        w = 1;
                    }
                    w = w * 100 + '%';
                }
            }

            return {
                top: p.top || 0,
                left: p.left || 0,
                height: h,
                width: w
            };
        },

        getSize: function ($dom) {
            var $wrap, $clone, sizes;
            if (!$dom.is(':visible')) {
                $wrap = $("<div />").appendTo($("body"));
                $wrap.css({
                    "position": "absolute !important",
                    "visibility": "hidden !important",
                    "display": "block !important"
                });

                $clone = $dom.clone().appendTo($wrap);

                sizes = {
                    width: $clone.outerWidth(),
                    height: $clone.outerHeight()
                };

                $wrap.remove();
            } else {
                sizes = {
                    width: $dom.outerWidth(),
                    height: $dom.outerHeight()
                };
            }

            return sizes;
        },

        getWidthStyle: function (w, dropdown) {
            if (this.options.responsive && !$.isNumeric(w)) {
                return 'width:' + w + ';';
            } else {
                return 'width:' + (dropdown ? Math.max(320, w) : w) + 'px;';
            }
        },

        bind: function () {
            var $this = this;

            $(document).on('click', (this._mouteclick = function (e) {
                var $target = $(e.target);
                var $dropdown, $span, $input;
                if ($target.is('.city-picker-span')) {
                    $span = $target;
                } else if ($target.is('.city-picker-span *')) {
                    $span = $target.parents('.city-picker-span');
                }
                if ($target.is('.city-picker-input')) {
                    $input = $target;
                }
                if ($target.is('.city-picker-dropdown')) {
                    $dropdown = $target;
                } else if ($target.is('.city-picker-dropdown *')) {
                    $dropdown = $target.parents('.city-picker-dropdown');
                }
                if ((!$input && !$span && !$dropdown) ||
                    ($span && $span.get(0) !== $this.$textspan.get(0)) ||
                    ($input && $input.get(0) !== $this.$element.get(0)) ||
                    ($dropdown && $dropdown.get(0) !== $this.$dropdown.get(0))) {
                    $this.close(true);
                }

            }));

            this.$element.on('change', (this._changeElement = $.proxy(function () {
                this.close(true);
                this.refresh(true);
            }, this))).on('focus', (this._focusElement = $.proxy(function () {
                this.needBlur = true;
                this.open();
            }, this))).on('blur', (this._blurElement = $.proxy(function () {
                if (this.needBlur) {
                    this.needBlur = false;
                    this.close(true);
                }
            }, this)));

            this.$textspan.on('click', function (e) {
                var $target = $(e.target), type;
                $this.needBlur = false;
                if ($target.is('.select-item')) {
                    type = $target.data('count');
                    $this.open(type);
                } else {
                    if ($this.$dropdown.is(':visible')) {
                        $this.close();
                    } else {
                        $this.open();
                    }
                }
            }).on('mousedown', function () {
                $this.needBlur = false;
            });

            this.$dropdown.on('click', '.city-select a', function () {
                var $select = $(this).parents('.city-select');
                var $active = $select.find('a.active');
                var last = $select.next().length === 0;
                $active.removeClass('active');
                $(this).addClass('active');
                if ($active.data('code') !== $(this).data('code')) {
                    $select.data('item', {
                        address: $(this).attr('title'), code: $(this).data('code')
                    });
                  $this.options[PROVINCE] = undefined;
                  $this.options[CITY] = undefined;
                  $this.options[DISTRICT] = undefined;
                  $(this).trigger(EVENT_CHANGE);
                    $this.feedText();
                    $this.feedVal();
                    if (last) {
                        $this.close();
                    }
                }
            }).on('click', '.city-select-tab a', function () {
                if (!$(this).hasClass('active')) {
                    var type = $(this).data('count');
                    $this.tab(type);
                }
            }).on('mousedown', function () {
                $this.needBlur = false;
            });

            if (this.$province) {
                this.$province.on(EVENT_CHANGE, (this._changeProvince = $.proxy(function () {
                    this.output(CITY);
                    this.output(DISTRICT);
                    this.tab(CITY);
                }, this)));
            }

            if (this.$city) {
                this.$city.on(EVENT_CHANGE, (this._changeCity = $.proxy(function () {
                    this.output(DISTRICT);
                    this.tab(DISTRICT);
                }, this)));
            }
        },

        open: function (type) {
            type = type || PROVINCE;
            this.$dropdown.show();
            this.$textspan.addClass('open').addClass('focus');
            this.tab(type);
        },

        close: function (blur) {
            this.$dropdown.hide();
            this.$textspan.removeClass('open');
            if (blur) {
                this.$textspan.removeClass('focus');
            }
        },

        unbind: function () {

            $(document).off('click', this._mouteclick);

            this.$element.off('change', this._changeElement);
            this.$element.off('focus', this._focusElement);
            this.$element.off('blur', this._blurElement);

            this.$textspan.off('click');
            this.$textspan.off('mousedown');

            this.$dropdown.off('click');
            this.$dropdown.off('mousedown');

            if (this.$province) {
                this.$province.off(EVENT_CHANGE, this._changeProvince);
            }

            if (this.$city) {
                this.$city.off(EVENT_CHANGE, this._changeCity);
            }
        },

        getText: function () {
            var text = '';
            this.$dropdown.find('.city-select')
                .each(function () {
                    var item = $(this).data('item'),
                        type = $(this).data('count');
                    if (item) {
                        text += ($(this).hasClass('province') ? '' : '/') + '<span class="select-item" data-count="' +
                            type + '" data-code="' + item.code + '">' + item.address + '</span>';
                    }
                });
            return text;
        },

        getPlaceHolder: function () {
            return this.$element.attr('placeholder') || this.options.placeholder;
        },

        feedText: function () {
            var text = this.getText();
            if (text) {
                this.$textspan.find('>.placeholder').hide();
                this.$textspan.find('>.title').html(this.getText()).show();
            } else {
                this.$textspan.find('>.placeholder').text(this.getPlaceHolder()).show();
                this.$textspan.find('>.title').html('').hide();
            }
        },

        getVal: function () {
            var text = '';
            this.$dropdown.find('.city-select')
                .each(function () {
                    var item = $(this).data('item');
                    if (item) {
                        text += ($(this).hasClass('province') ? '' : '/') + item.address;
                    }
                });
            return text;
        },

        feedVal: function () {
            //this.$element.val(this.getVal()); zhugl 修改2016/11/01
            // zhugl 修改2016/11/01 START
            var cantonCode = "";
            if (this.$district.find('a.active').length > 0) {
                cantonCode = this.$district.find('a.active').attr("data-code");
            } else if (this.$city.find('a.active').length > 0) {
                cantonCode = this.$city.find('a.active').attr("data-code");
            } else if (this.$province.find('a.active').length > 0) {
                cantonCode = this.$province.find('a.active').attr("data-code");
            }
            this.$element.val(cantonCode);
            // zhugl 修改2016/11/01 END
        },

        output: function (type) {
            var options = this.options;
            //var placeholders = this.placeholders;
            var $select = this['$' + type];
            var data = type === PROVINCE ? {} : [];
            var item;
            var districts;
            var code;
            var matched = null;
            var value;

            if (!$select || !$select.length) {
                return;
            }

            item = $select.data('item');

            if (digitReg.test(options[type])) {
                value = (item ? item.code : null) || options[type];
            } else {
                value = (item ? item.address : null) || options[type];
            }
            //zhanghj update on 2017/8/3
            code = (
                type === PROVINCE ? 86 :
                    type === CITY ? this.$province && this.$province.find('.active').data('code')||options[PROVINCE] :
                        type === DISTRICT ? this.$city && this.$city.find('.active').data('code')||options[CITY] : code
            );

            if (!ChineseDistricts.hasOwnProperty(code) && $.isNumeric(code)) {
                loadSubDistricts (code);
            }
            
            districts = $.isNumeric(code) ? ChineseDistricts[code] : null;

            if ($.isPlainObject(districts)) {
                $.each(districts, function (code, address) {
                    var provs;
                    if (type === PROVINCE) {
                        provs = [];
                        for (var i = 0; i < address.length; i++) {
                            if (digitReg.test(value)) {// value 时区域代码 
                                if (address[i].code === value) {
                                    matched = {
                                        code: address[i].code,
                                        address: address[i].address
                                    };
                                }
                                provs.push({
                                    code: address[i].code,
                                    address: address[i].address,
                                    selected: address[i].code === value
                                });
                            } else {
                                if (address[i].address === value) {
                                    matched = {
                                        code: address[i].code,
                                        address: address[i].address
                                    };
                                }
                                
                                provs.push({
                                    code: address[i].code,
                                    address: address[i].address,
                                    selected: address[i].address === value
                                });
                            }
                        }
                        data[code] = provs;
                    } else {
                        if (digitReg.test(value)) {// value 时区域代码 
                            if (code === value) {
                                matched = {
                                    code: code,
                                    address: address
                                };
                            }
                            
                            data.push({
                                code: code,
                                address: address,
                                selected: code === value
                            });
                        } else {
                            if (address === value) {
                                matched = {
                                    code: code,
                                    address: address
                                };
                            }
                            data.push({
                                code: code,
                                address: address,
                                selected: address === value
                            });
                        }
                    }
                });
            }

            $select.html(type === PROVINCE ? this.getProvinceList(data) :
                this.getList(data, type));
            $('#totalProvince').on('click',function () {
                $('.city-select.province a.active').removeClass('active');
                $(this).addClass('active');
                $('.city-picker-dropdown').hide();
                $('#_area').val('');
                $('.city-picker-span .title').html('');
                $('.city-picker-span .placeholder').show();
                $("#cantonCode").val("");
                return false;
            });
            $('#totalCity').on('click',function () {
                $('.city-select.city a.active').removeClass('active');
                $(this).addClass('active');
                $('.city-picker-dropdown').hide();
                $('#_area').val($(this).attr('data-code'));
                var html=$('.city-picker-span .title').html();
                $('.city-picker-span .title').html($('.city-picker-span .title').children().eq(0));
              $("#cantonCode").val($(this).attr('data-code'));
              return false;
            });
            $('#totalDistrict').on('click',function () {
                $('.city-select.district a.active').removeClass('active');
                $(this).addClass('active');
                $('.city-picker-dropdown').hide();
                $('#_area').val($(this).attr('data-code'));
                var e1=$('.city-picker-span .title').children().eq(0);
                var e2=$('.city-picker-span .title').children().eq(1);
                $('.city-picker-span .title').html(e1);
                $('.city-picker-span .title').append('/');
                $('.city-picker-span .title').append(e2);
              $("#cantonCode").val($(this).attr('data-code'));
              return false;
            });
            $select.data('item', matched);
        },
        getProvinceList: function (data) {
            var list = [],
                $this = this,
                simple = this.options.simple;
            list.push('<dl class="clearfix"><dt>&nbsp;</dt><dd><a id="totalProvince">全部地区</a></dd></dl>');
            $.each(data, function (i, n) {
                list.push('<dl class="clearfix">');
                list.push('<dt>' + i + '</dt><dd>');
                $.each(n, function (j, m) {
                    list.push(
                        '<a' +
                        ' title="' + (m.address || '') + '"' +
                        ' data-code="' + (m.code || '') + '"' +
                        ' class="' +
                        (m.selected ? ' active' : '') +
                        '">' +
                        ( simple ? $this.simplize(m.address, PROVINCE) : m.address) +
                        '</a>');
                });
                list.push('</dd></dl>');
            });

            return list.join('');
        },

        getList: function (data, type) {
            var list = [],
                $this = this,
                simple = this.options.simple;
            list.push('<dl class="clearfix"><dd>');
            if(type=='city'&&data.length>0){
                var code=data[0]['code'];
                var provinceCode=code.substring(0,2)+'0000';
                if(data.length!=1){
                    list.push('<a title=".." id="totalCity" data-code="'+provinceCode+'" class="">全省</a>');
                }
            }else if(type=='district'&&data.length>0){
                var code=data[0]['code'];
                var cityCode=code.substring(0,4)+'00';
                list.push('<a title=".." id="totalDistrict" data-code="'+cityCode+'" class="">全市</a>');
            }
            $.each(data, function (i, n) {
                list.push(
                    '<a' +
                    ' title="' + (n.address || '') + '"' +
                    ' data-code="' + (n.code || '') + '"' +
                    ' class="' +
                    (n.selected ? ' active' : '') +
                    '">' +
                    ( simple ? $this.simplize(n.address, type) : n.address) +
                    '</a>');
            });
            list.push('</dd></dl>');

            return list.join('');
        },

        simplize: function (address, type) {
            address = address || '';
            if (type === PROVINCE) {
                return address.replace(/[省,市,自治区,壮族,回族,维吾尔]/g, '');
            } else if (type === CITY) {
                return address.replace(/[市,地区,回族,蒙古,苗族,白族,傣族,景颇族,藏族,彝族,壮族,傈僳族,布依族,侗族]/g, '')
                    .replace('哈萨克', '').replace('自治州', '').replace(/自治县/, '');
            } else if (type === DISTRICT) {
                return address.length > 2 ? address.replace(/[市,区,县,旗]/g, '') : address;
            }
        },

        tab: function (type) {
            var $selects = this.$dropdown.find('.city-select');
            var $tabs = this.$dropdown.find('.city-select-tab > a');
            var $select = this['$' + type];
            var $tab = this.$dropdown.find('.city-select-tab > a[data-count="' + type + '"]');
            if ($select) {
                $selects.hide();
                $select.show();
                $tabs.removeClass('active');
                $tab.addClass('active');
            }
        },

        reset: function () {
            this.$element.val(null).trigger('change');
        },

        destroy: function () {
            this.unbind();
            this.$element.removeData(NAMESPACE).removeClass('city-picker-input');
            this.$textspan.remove();
            this.$dropdown.remove();
        }
    };

    CityPicker.DEFAULTS = {
        simple: false,
        responsive: false,
        placeholder: '请选择省/市/区',
        level: 'district',
        province: '',
        city: '',
        district: ''
    };

    CityPicker.setDefaults = function (options) {
        $.extend(CityPicker.DEFAULTS, options);
    };

    // Save the other citypicker
    CityPicker.other = $.fn.citypicker;

    // Register as jQuery plugin
    $.fn.citypicker = function (option) {
        var args = [].slice.call(arguments, 1);

        return this.each(function () {
            var $this = $(this);
            var data = $this.data(NAMESPACE);
            var options;
            var fn;

            if (!data) {
                if (/destroy/.test(option)) {
                    return;
                }

                options = $.extend({}, $this.data(), $.isPlainObject(option) && option);
                $this.data(NAMESPACE, (data = new CityPicker(this, options)));
            }

            if (typeof option === 'string' && $.isFunction(fn = data[option])) {
                fn.apply(data, args);
            }
        });
    };

    $.fn.citypicker.Constructor = CityPicker;
    $.fn.citypicker.setDefaults = CityPicker.setDefaults;

    // No conflict
    $.fn.citypicker.noConflict = function () {
        $.fn.citypicker = CityPicker.other;
        return this;
    };
    
    // 点击省市县加载其对应的下级区域
    function loadSubDistricts(parentCantonCode){
        $.ajax({
            async: false,
            url: $("#appPath").val() + "/fw/syscanton/getCityDistricts.htm?ticketId=" + $("#ticketId").val(),
            data: {
                "parentCantonCode":parentCantonCode
            },
            success: function(result){
                var obj = $.parseJSON(result);
                if(obj.status == 1){
                    if (obj.data.value) {
                        ChineseDistricts[parentCantonCode] = obj.data.value[parentCantonCode];
                    }
                }else{
                    $.notify("根据区域代码加载其子区域代码时异常",{"status":"info"});
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                $.notify("请求根据区域代码加载其子区域代码时错误",{"status":"info"});
            }
        });
    }

    /*$(function () {
        $('[data-toggle="city-picker"]').citypicker();
    });*/  
});