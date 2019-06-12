<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#import "../fragments/common-layout.ftl" as common>
<#macro template title nestedComponent="" showNavigationDriver=true center=false menu=[]>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><#if title??>${title}</#if></title>
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Material+Icons" rel="stylesheet"
          type="text/css">
    <link href="/static/css/vuetify.css" rel="stylesheet" type="text/css">
    <link href="/static/css/style.css" rel="stylesheet" type="text/css">
    <link href="/static/css/custom-styles.css" rel="stylesheet" type="text/css">

</head>
<body>
<div id="app">
    <div do-not-show v-cloak style="height: 100%;">
        <div class="loader"></div>
    </div>
    <v-app v-cloak>
        <#if showNavigationDriver>
            <@common.navigationDriver></@common.navigationDriver>
        </#if>
        <@common.toolbar showNavigationDriver=showNavigationDriver></@common.toolbar>
        <main style="height: 100%;" class="main-image">
            <v-content class="v-content_mode height-100">
                <v-container style="max-width: 1185px !important;" fluid align-center fill-height>
                    <v-layout align-center justify-center row fill-height>
                        <v-flex xl8 lg8 md9 sm12 xs12 <#if !center>class="height-100"</#if>>
                            <#nested />
                        </v-flex>
                    </v-layout>
                </v-container>
            </v-content>
        </main>

        <@common.footer></@common.footer>
    </v-app>
</div>

<script src="/static/js/vue.js"></script>
<script src="/static/js/axios.js"></script>
<script src="/static/js/vuetify.js"></script>
<script src="/static/js/moment.js"></script>
<script>
    var LocalStorageManager = {
        get: function (key, defaultValue) {
            var item = localStorage.getItem(key);
            var val = JSON.parse(item === undefined ? null : item);
            return val !== null ? val : defaultValue;
        },
        set: function (key, val) {
            if (val)
                localStorage.setItem(key, JSON.stringify(val));
        }
    };

    function redirect(url) {
        var ua = navigator.userAgent.toLowerCase(),
                isIE = ua.indexOf('msie') !== -1,
                version = parseInt(ua.substr(4, 2), 10);

        if (isIE && version < 9) {
            var link = document.createElement('a');
            link.href = url;
            document.body.appendChild(link);
            link.click();
        }
        else {
            window.location.href = url;
        }
    }

    moment.locale('pl');
    Vue.prototype.$http = axios;
    Vue.prototype.$moment = moment;

    Vue.filter('lowercase', function (value) {
        return value.toLowerCase();
    });

    var timeMixin = {
        methods: {
            time: function (time) {
                return this.$moment(time, 'YYYY-MM-DD HH:mm', true).format('HH:mm');
            },
            fullDate: function (fullDate) {
                return this.$moment(fullDate, 'YYYY-MM-DD HH:mm', true).format('D MMMM YYYY [<@spring.message code="common.at"/>] HH:mm');
            }
        }
    }

    Vue.filter('money', function (value) {
        var formatMoney = function (n) {
            var c = 2;
            var d = ',';
            var t = '&nbsp;';
            var s = n < 0 ? "-" : "";

            var i = String(parseInt(n = Math.abs(Number(n) || 0).toFixed(c)));

            var j = i.length > 3 ? i.length % 3 : 0;

            return s + (j ? i.substr(0, j) + t : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + t) + (d + Math.abs(n - i).toFixed(c).slice(2)) + '&nbsp;zł';
        };

        return formatMoney(Number(value));
    });

    Vue.prototype.$rules = {
        required: function (value) {
            return !!value || '<@spring.message code="common.rules.required"/>';
        },
        email: function (value) {
            return /.+@.+/.test(value) || '<@spring.message code="common.rules.email"/>';
        },
        hour: function (value) {
            var isValid = /^([0-1][0-9]|2[0-4]):([0-5][0-9])$/.test(value);
            return isValid || '<@spring.message code="common.rules.hour"/>';
        },
        date: function (value) {
            return moment(value, 'DD.MM.YYYY', true).isValid() || '<@spring.message code="common.rules.date"/>';
        }
    };
</script>

<#include "../fragments/common-components.ftl">

<#attempt>
    <#include "${nestedComponent}">
    <#recover>
    <script>
        console.error('error load component');
    </script>
</#attempt>
<script>
    new Vue({
        el: '#app',
        data: {
            layout: {
                clipped: true,
                drawer: false
            },
            account: {
                menu: false
            }<#if menu??>,
                menu: ${JSONFactory.stringify(menu)}
            </#if>
        },
        methods: {
            changeMode: function (mode) {
                LocalStorageManager.set('mode', mode);
                document.body.setAttribute('mode', mode);
            },
            changeLang: function (lang) {
                this.$http.get('/lang', {
                    params: {
                        lang: lang
                    }
                }).then(function (response) {
                    location.reload();
                }).catch(function (error) {
                    alert('Change language error');
                })
            }
        },
        mounted: function () {
            var mode = LocalStorageManager.get('mode', 1);
            this.changeMode(mode);
        }
    });
</script>
</body>
</html>
</#macro>