(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-10258d88"],{1425:function(e,t,n){},3869:function(e,t,n){"use strict";n("1425")},f034:function(e,t,n){"use strict";n.r(t);var c=n("7a23"),r=Object(c["X"])("data-v-4af85184");Object(c["H"])("data-v-4af85184");var a={class:"content"},i={style:{"text-align":"center"}},u={style:{"text-align":"right"}},o=Object(c["m"])("编辑"),d=Object(c["m"])("删除"),s=Object(c["m"])("返回");Object(c["E"])();var l=r((function(e,t,n,l,b,f){var h=Object(c["L"])("a-button"),j=Object(c["L"])("a-popconfirm"),m=Object(c["L"])("a-space"),O=Object(c["L"])("a-divider");return Object(c["D"])(),Object(c["j"])("div",a,[Object(c["n"])("h1",i,Object(c["N"])(e.document.title),1),Object(c["n"])("div",u,[Object(c["n"])(m,null,{default:r((function(){return[e.document.canEdit?(Object(c["D"])(),Object(c["j"])(h,{key:0,type:"primary",onClick:e.edit},{default:r((function(){return[o]})),_:1},8,["onClick"])):Object(c["k"])("",!0),Object(c["n"])(j,{title:"您确定要删除文档吗?","ok-text":"是","cancel-text":"否",onConfirm:e.onDelete},{default:r((function(){return[e.document.canDelete?(Object(c["D"])(),Object(c["j"])(h,{key:0,type:"danger"},{default:r((function(){return[d]})),_:1})):Object(c["k"])("",!0)]})),_:1},8,["onConfirm"]),Object(c["n"])(h,{onClick:e.back},{default:r((function(){return[s]})),_:1},8,["onClick"])]})),_:1})]),Object(c["n"])(O),Object(c["n"])("div",{style:{width:"100%"},innerHTML:e.document.html},null,8,["innerHTML"])])})),b=n("1da1"),f=n("d4ec"),h=n("bee2"),j=n("262e"),m=n("2caf"),O=(n("96cf"),n("a9e3"),n("5319"),n("ac1f"),n("9ab4")),p=n("ce1f"),k=n("f64c"),v=n("88db"),g=n("7c5c"),y=n("1487"),w=n.n(y),x=(n("8da8"),function(e){Object(j["a"])(n,e);var t=Object(m["a"])(n);function n(){var e;return Object(f["a"])(this,n),e=t.apply(this,arguments),e.docId=0,e.document={},e}return Object(h["a"])(n,[{key:"mounted",value:function(){var e=Object(b["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:if(this.$route.query.docId){e.next=2;break}return e.abrupt("return",!1);case 2:return this.docId=Number(this.$route.query.docId),e.next=5,v["a"].getDoc(this.docId);case 5:t=e.sent,0!==t.code?k["a"].error(t.msg):(this.document=t.data,1===this.document.contentType?this.document.content&&this.renderMarkdown(this.document.content):this.document.html=this.document.content);case 7:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()},{key:"renderMarkdown",value:function(e){if(!e)return!1;this.document.html=g["marked"].parse(e,{renderer:new g["marked"].Renderer,highlight:function(e,t){var n=w.a.getLanguage(t)?t:"plaintext";return w.a.highlight(e,{language:n}).value},langPrefix:"hljs language-",pedantic:!1,gfm:!0,breaks:!1,sanitize:!1,smartypants:!1,xhtml:!1})}},{key:"back",value:function(){this.$router.back()}},{key:"edit",value:function(){this.$router.replace({path:"/doc-edit",query:{docId:this.docId}})}},{key:"onDelete",value:function(){var e=Object(b["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,v["a"].deleteDoc(this.docId);case 2:t=e.sent,0===t.code?this.$router.back():k["a"].error(t.msg);case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()}]),n}(p["b"]));x=Object(O["a"])([Object(p["a"])({components:{}})],x);var I=x;n("3869");I.render=l,I.__scopeId="data-v-4af85184";t["default"]=I}}]);
//# sourceMappingURL=chunk-10258d88.380f9517.js.map