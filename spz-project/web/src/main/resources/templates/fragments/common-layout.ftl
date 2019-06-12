<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />

<#macro navigationDriver>
    <v-navigation-drawer fixed :clipped="layout.clipped" v-model="layout.drawer" hide-overlay
                         disable-resize-watcher app>
        <v-list dense expand>
            <v-list-tile :value="true" v-for="(item, index) in menu" :href="item.to" :key="index">
                <v-list-tile-action>
                    <v-icon v-html="item.icon"></v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    <v-list-tile-title v-text="item.title"></v-list-tile-title>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-navigation-drawer>
</#macro>

<#macro toolbar showNavigationDriver>
    <v-toolbar fixed dense app :clipped-left="layout.clipped"
               class="v-toolbar_mode">
        <#if showNavigationDriver>
            <v-toolbar-side-icon @click.native.stop="layout.drawer = !layout.drawer"></v-toolbar-side-icon>
        </#if>

        <v-toolbar-title>
            <a href="/" style="text-decoration: none; color: currentColor;"><@spring.message code="common.title"/></a>

        </v-toolbar-title>

        <v-spacer></v-spacer>

        <v-toolbar-items>
            <v-divider vertical></v-divider>
            <@security.authorize access="!isAuthenticated()">
                <v-btn @click.native.stop=";" href="/auth/login" flat><@spring.message code="common.sign_in"/></v-btn>
            </@security.authorize>
          <@security.authorize access="isAuthenticated()">
          <v-menu v-model="account.menu"
                  attach
                  :close-on-content-click="false"
                  offset-y
                  left
          >
              <v-btn slot="activator" flat><@security.authentication property="principal.firstName" /></v-btn>
              <v-card>
                  <v-list>
                      <v-list-tile>
                          <v-list-tile-content>
                              <v-list-tile-title><@security.authentication property="principal.firstName" /> <@security.authentication property="principal.lastName" /></v-list-tile-title>
                              <v-list-tile-sub-title><@security.authentication property="principal.email" /></v-list-tile-sub-title>
                          </v-list-tile-content>

                      </v-list-tile>
                  </v-list>

                  <v-divider></v-divider>

                  <v-list>
                      <v-list-tile href="/auth/logout" @click=";">
                          <v-list-tile-title><@spring.message code="common.sign_out"/></v-list-tile-title>
                      </v-list-tile>
                  </v-list>

              </v-card>
          </v-menu>
          </@security.authorize>
        </v-toolbar-items>
    </v-toolbar>
</#macro>

<#macro footer>
  <v-footer height="auto" class="v-footer_action_mode" absolute inset app>
      <v-layout justify-center row wrap>
          <v-btn @click="changeLang('pl')" color="white" flat round>
              <@spring.message code="common.lang.pl"/>
          </v-btn>
          <v-btn @click="changeLang('en')" color="white" flat round>
              <@spring.message code="common.lang.en"/>
          </v-btn>
          <v-divider vertical></v-divider>
          <v-btn @click="changeMode('1')" color="white" flat round>
              <@spring.message code="common.color.blue"/>
          </v-btn>
          <v-btn @click="changeMode('2')" color="white" flat round>
              <@spring.message code="common.color.lime"/>
          </v-btn>
          <v-btn @click="changeMode('3')" color="white" flat round>
              <@spring.message code="common.color.red"/>
          </v-btn>
          <v-flex
                  class="v-footer_mode"
                  py-1
                  text-xs-center
                  xs12
          >
              <span>&copy; 2018 - 2019 <@spring.message code="common.title"/></span>
          </v-flex>
      </v-layout>
  </v-footer>
</#macro>