(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-abcdd0b8"],{1511:function(e,t,a){"use strict";a.r(t);a("1276"),a("ac1f"),a("d3b7"),a("25f0"),a("4d63"),a("99af");var n=a("7a23"),r=Object(n["X"])("data-v-dd77fd1e");Object(n["G"])("data-v-dd77fd1e");var c=Object(n["m"])("a",{href:"javascript:void(0);"},"删除",-1),o={style:{padding:"8px"}},u=Object(n["l"])(" Search "),i=Object(n["l"])(" Reset "),l={key:0};Object(n["D"])();var d=r((function(e,t,a,d,s,p){var f=Object(n["K"])("Group"),b=Object(n["K"])("a-layout-sider"),O=Object(n["K"])("a-popconfirm"),h=Object(n["K"])("a-space"),m=Object(n["K"])("a-input"),j=Object(n["K"])("SearchOutlined"),y=Object(n["K"])("a-button"),v=Object(n["K"])("search-outlined"),g=Object(n["K"])("a-switch"),k=Object(n["K"])("a-table"),D=Object(n["K"])("a-layout-content"),x=Object(n["K"])("a-layout");return Object(n["C"])(),Object(n["j"])(x,{class:"a-layout"},{default:r((function(){return[Object(n["m"])(b,{class:"a-layout-sider",onContextmenu:t[1]||(t[1]=Object(n["W"])((function(){}),["prevent"]))},{default:r((function(){return[Object(n["m"])(f,{onSelect:e.onSelectGroup},null,8,["onSelect"])]})),_:1}),Object(n["m"])(D,{class:"a-layout-content"},{default:r((function(){return[Object(n["m"])(k,{class:"ant-table-striped",rowKey:"id",columns:e.columns,"data-source":e.users,rowClassName:function(e,t){return t%2===1?"table-striped":null},pagination:e.pagination,onChange:e.handleTableChange},{operation:r((function(t){var a=t.record;return[Object(n["m"])(h,null,{default:r((function(){return[e.users.length?(Object(n["C"])(),Object(n["j"])(O,{key:0,title:"您确定要删除吗?",onConfirm:function(t){return e.onUserDel(a)}},{default:r((function(){return[c]})),_:2},1032,["onConfirm"])):Object(n["k"])("",!0),Object(n["m"])("a",{href:"javascript:void(0);",onClick:function(t){return e.onUserTop(a)}},"置顶",8,["onClick"]),Object(n["m"])("a",{href:"javascript:void(0);",onClick:function(t){return e.onUserEdit(a)}},"编辑",8,["onClick"])]})),_:2},1024)]})),filterDropdown:r((function(t){var a=t.setSelectedKeys,c=t.selectedKeys,l=t.confirm,d=t.clearFilters,s=t.column;return[Object(n["m"])("div",o,[Object(n["m"])(m,{ref:"searchInput",placeholder:"搜索 ".concat(s.title),value:c[0],style:{width:"188px","margin-bottom":"8px",display:"block"},onChange:function(e){return a(e.target.value?[e.target.value]:[])},onPressEnter:function(t){return e.handleSearch(c,l,s.dataIndex)}},null,8,["placeholder","value","onChange","onPressEnter"]),Object(n["m"])(y,{type:"primary",size:"small",style:{width:"90px","margin-right":"8px"},onClick:function(t){return e.handleSearch(c,l,s.dataIndex)}},{icon:r((function(){return[Object(n["m"])(j)]})),default:r((function(){return[u]})),_:2},1032,["onClick"]),Object(n["m"])(y,{size:"small",style:{width:"90px"},onClick:function(t){return e.handleReset(d)}},{default:r((function(){return[i]})),_:2},1032,["onClick"])])]})),filterIcon:r((function(e){return[Object(n["m"])(v,{style:{color:e?"#108ee9":void 0}},null,8,["style"])]})),name:r((function(t){var a=t.text,r=t.column;return[e.searchText&&e.searchedColumn===r.dataIndex?(Object(n["C"])(),Object(n["j"])("span",l,[(Object(n["C"])(!0),Object(n["j"])(n["b"],null,Object(n["J"])(a.toString().split(new RegExp("(?<=".concat(e.searchText,")|(?=").concat(e.searchText,")"),"i")),(function(t,a){return Object(n["C"])(),Object(n["j"])(n["b"],null,[t.toLowerCase()===e.searchText.toLowerCase()?(Object(n["C"])(),Object(n["j"])("mark",{class:"highlight",key:a},Object(n["M"])(t),1)):(Object(n["C"])(),Object(n["j"])(n["b"],{key:1},[Object(n["l"])(Object(n["M"])(t),1)],64))],64)})),256))])):(Object(n["C"])(),Object(n["j"])(n["b"],{key:1},[Object(n["l"])(Object(n["M"])(a),1)],64))]})),status:r((function(t){var a=t.record;return[Object(n["m"])(g,{checked:1==a.status,onClick:function(t){return e.onUserChange(a)}},null,8,["checked","onClick"])]})),_:1},8,["columns","data-source","rowClassName","pagination","onChange"])]})),_:1})]})),_:1})})),s=a("1da1"),p=a("d4ec"),f=a("bee2"),b=a("262e"),O=a("2caf"),h=(a("b0c0"),a("96cf"),a("9ab4")),m=a("f64c"),j=a("ce1f"),y=a("e9d5"),v=Object(n["l"])("新增部门"),g=Object(n["l"])("新增单位"),k=Object(n["l"])("更新单位"),D=Object(n["l"])(" 删除单位 "),x=Object(n["l"])("新增人员"),w=Object(n["l"])("更新部门"),C=Object(n["l"])("删除部门");function _(e,t,a,r,c,o){var u=Object(n["K"])("a-menu-item"),i=Object(n["K"])("a-menu"),l=Object(n["K"])("a-dropdown"),d=Object(n["K"])("a-tree");return Object(n["C"])(),Object(n["j"])(d,{"load-data":e.onLoadData,"tree-data":e.treeData,expandedKeys:e.expandedKeys,"onUpdate:expandedKeys":t[2]||(t[2]=function(t){return e.expandedKeys=t}),draggable:"",onSelect:e.onSelect,style:{height:"100%"},onContextmenu:t[3]||(t[3]=Object(n["W"])((function(){}),["prevent"])),onDrop:e.onDrop},{title:Object(n["U"])((function(a){return[Object(n["m"])(l,{trigger:["contextmenu"]},{overlay:Object(n["U"])((function(){return[Object(n["m"])(i,{onClick:function(t){return e.onContextMenuClick(a,t)}},{default:Object(n["U"])((function(){return[0==a.type||1==a.type?(Object(n["C"])(),Object(n["j"])(n["b"],{key:0},[Object(n["m"])(u,{key:e.contextMenuKeys.ADD_DEPT},{default:Object(n["U"])((function(){return[v]})),_:1}),Object(n["m"])(u,{key:e.contextMenuKeys.ADD_GROUP},{default:Object(n["U"])((function(){return[g]})),_:1}),Object(n["m"])(u,{key:e.contextMenuKeys.UPDATE_GROUP},{default:Object(n["U"])((function(){return[k]})),_:1}),null!=a.parentId?(Object(n["C"])(),Object(n["j"])(u,{key:e.contextMenuKeys.DELETE_GROUP},{default:Object(n["U"])((function(){return[D]})),_:1})):Object(n["k"])("",!0)],64)):Object(n["k"])("",!0),2==a.type?(Object(n["C"])(),Object(n["j"])(n["b"],{key:1},[Object(n["m"])(u,{key:e.contextMenuKeys.ADD_USER},{default:Object(n["U"])((function(){return[x]})),_:1}),Object(n["m"])(u,{key:e.contextMenuKeys.UPDATE_DEPT},{default:Object(n["U"])((function(){return[w]})),_:1}),Object(n["m"])(u,{key:e.contextMenuKeys.DELETE_DEPT},{default:Object(n["U"])((function(){return[C]})),_:1})],64)):Object(n["k"])("",!0)]})),_:2},1032,["onClick"])]})),default:Object(n["U"])((function(){return[Object(n["m"])("span",{onContextmenu:t[1]||(t[1]=Object(n["W"])((function(){}),["prevent"]))},Object(n["M"])(a.title),33)]})),_:2},1024)]})),_:1},8,["load-data","tree-data","expandedKeys","onSelect","onDrop"])}var U=a("2909"),I=(a("a434"),a("d81d"),a("159b"),a("a9e3"),a("ed3b")),T=a("45eb"),R=a("7e84"),E=a("799e"),K=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){return Object(p["a"])(this,a),t.apply(this,arguments)}return Object(f["a"])(a,[{key:"find",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/organization/get/".concat(e))}},{key:"delete",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/organization/delete/".concat(e))}},{key:"add",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/organization/add",e)}},{key:"update",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/organization/update",e)}},{key:"loadGroupNodes",value:function(){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/organization/load-nodes")}},{key:"children",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/organization/children"+(e?"?parentId=".concat(e):""))}},{key:"dropSort",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/organization/drop-sort",e)}}]),a}(E["a"]),P=new K,S=P,N=Object(n["X"])("data-v-61621a96");Object(n["G"])("data-v-61621a96");var L={key:1},A=Object(n["m"])("div",{class:"ant-upload-text"},"Upload",-1),G=Object(n["l"])("保存"),M=Object(n["l"])("取消");Object(n["D"])();var z=N((function(e,t,a,r,c,o){var u=Object(n["K"])("a-col"),i=Object(n["K"])("loading-outlined"),l=Object(n["K"])("plus-outlined"),d=Object(n["K"])("a-upload"),s=Object(n["K"])("UserOutlined"),p=Object(n["K"])("a-input"),f=Object(n["K"])("a-form-item"),b=Object(n["K"])("LockOutlined"),O=Object(n["K"])("a-row"),h=Object(n["K"])("MobileOutlined"),m=Object(n["K"])("MailOutlined"),j=Object(n["K"])("a-tree-select"),y=Object(n["K"])("a-switch"),v=Object(n["K"])("a-button"),g=Object(n["K"])("a-form");return Object(n["C"])(),Object(n["j"])(g,{ref:"formRef",colon:!1,model:e.user,rules:e.rules,"label-col":{span:4},"wrapper-col":{span:18}},{default:N((function(){return[Object(n["m"])(O,{type:"flex",gutter:8,style:{margin:"0px"}},{default:N((function(){return[Object(n["m"])(u,{span:4}),Object(n["m"])(u,{span:6},{default:N((function(){return[Object(n["m"])(d,{name:"file",data:e.avatarData,"list-type":"picture-card",class:"avatar-uploader","show-upload-list":!1,action:"/file/upload",withCredentials:!0,onChange:e.handleUploadChange,"before-upload":e.beforeUpload},{default:N((function(){return[e.user.avatar?(Object(n["C"])(),Object(n["j"])("img",{key:0,src:e.avatarUrl,alt:"头像"},null,8,["src"])):(Object(n["C"])(),Object(n["j"])("div",L,[e.avatarLoading?(Object(n["C"])(),Object(n["j"])(i,{key:0})):(Object(n["C"])(),Object(n["j"])(l,{key:1})),A]))]})),_:1},8,["data","onChange","before-upload"])]})),_:1}),Object(n["m"])(u,{span:12},{default:N((function(){return[Object(n["m"])(f,{"wrapper-col":{flex:"auto"},name:"name"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.name,"onUpdate:value":t[1]||(t[1]=function(t){return e.user.name=t}),placeholder:"姓名"},{prefix:N((function(){return[Object(n["m"])(s,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1}),Object(n["m"])(f,{"wrapper-col":{flex:"auto"},name:"account"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.account,"onUpdate:value":t[2]||(t[2]=function(t){return e.user.account=t}),placeholder:"账号"},{prefix:N((function(){return[Object(n["m"])(s,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1}),Object(n["m"])(f,{"wrapper-col":{flex:"auto"},name:"password"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.password,"onUpdate:value":t[3]||(t[3]=function(t){return e.user.password=t}),type:"password",placeholder:"密码"},{prefix:N((function(){return[Object(n["m"])(b,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1})]})),_:1})]})),_:1}),Object(n["m"])(f,{label:"手机",name:"phone"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.phone,"onUpdate:value":t[4]||(t[4]=function(t){return e.user.phone=t}),placeholder:"手机"},{prefix:N((function(){return[Object(n["m"])(h,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1}),Object(n["m"])(f,{label:"邮箱",name:"email"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.email,"onUpdate:value":t[5]||(t[5]=function(t){return e.user.email=t}),placeholder:"邮箱"},{prefix:N((function(){return[Object(n["m"])(m,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1}),Object(n["m"])(f,{label:"职务"},{default:N((function(){return[Object(n["m"])(p,{value:e.user.post,"onUpdate:value":t[6]||(t[6]=function(t){return e.user.post=t}),placeholder:"职务"},{prefix:N((function(){return[Object(n["m"])(s,{style:{color:"rgba(0, 0, 0, 0.25)"}})]})),_:1},8,["value"])]})),_:1}),Object(n["m"])(f,{label:"部门"},{default:N((function(){return[Object(n["m"])(j,{"tree-data":e.treeData,"load-data":e.onLoadData,onChange:e.handleDeptChange,value:e.user.deptName,"onUpdate:value":t[7]||(t[7]=function(t){return e.user.deptName=t}),"tree-default-expand-all":"",style:{width:"100%"},placeholder:"请选择用户部门","dropdown-style":{maxHeight:"400px",overflow:"auto"}},null,8,["tree-data","load-data","onChange","value"])]})),_:1}),Object(n["m"])(f,{label:"状态"},{default:N((function(){return[Object(n["m"])(y,{checked:1==e.user.status,onClick:e.handleUserStatus},null,8,["checked","onClick"])]})),_:1}),Object(n["m"])(f,{"wrapper-col":{span:14,offset:4}},{default:N((function(){return[Object(n["m"])(v,{type:"primary",onClick:e.onSubmit},{default:N((function(){return[G]})),_:1},8,["onClick"]),Object(n["m"])(v,{style:{"margin-left":"10px"},onClick:e.onCancel},{default:N((function(){return[M]})),_:1},8,["onClick"])]})),_:1})]})),_:1},8,["model","rules"])})),Q=(a("7db0"),a("edc4")),$=a("6a86"),q=a("e8db"),J=a("cc68"),F=a("47ce"),W=a("8fe6"),H=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){return Object(p["a"])(this,a),t.apply(this,arguments)}return Object(f["a"])(a,[{key:"find",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/user/get/".concat(e))}},{key:"delete",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/user/delete/".concat(e))}},{key:"add",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/user/add",e)}},{key:"update",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/user/update",e)}},{key:"top",value:function(e,t){return Object(T["a"])(Object(R["a"])(a.prototype),"get",this).call(this,"/user/top?userId=".concat(e,"&deptId=").concat(t))}},{key:"pageList",value:function(e){return Object(T["a"])(Object(R["a"])(a.prototype),"post",this).call(this,"/user/page",e)}}]),a}(E["a"]),X=new H,B=X,V=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){var e;return Object(p["a"])(this,a),e=t.apply(this,arguments),e.user={status:1,deptId:e.deptId,deptName:e.deptName},e.avatarLoading=!1,e.treeData=[],e.formRef=Object(n["I"])(),e.rules={name:[{required:!0,message:"请输入姓名",trigger:"blur"},{max:20,message:"最多输入20个字符"}],account:[{required:!0,message:"请输入账号",trigger:"blur"},{max:20,message:"最多输入20个字符"}],post:[{max:20,message:"最多输入20个字符"}],password:[{required:!1,message:"请输入密码",trigger:"blur"},{min:6,max:15,message:"密码为6-15位字符串",trigger:"blur"}],phone:[{max:11,message:"手机号最多为11个数字",trigger:"blur"},{pattern:/^1[0-9]{10}$/,message:"手机号码错误",trigger:"blur"}],email:[{pattern:/^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,})$/,message:"邮箱地址错误",trigger:"blur"},{max:50,message:"最多输入50个字符"}]},e.handleUploadChange=function(t){if("uploading"===t.file.status)return e.avatarLoading=!0,!1;var a,n;(e.avatarLoading=!1,"done"===t.file.status)&&(0===(null===(a=t.file.response)||void 0===a?void 0:a.code)?(e.user.avatar=t.file.response.data.objectName,e.user.reavatar=e.user.avatar+"?random="+Math.random()):m["a"].error(null===(n=t.file.response)||void 0===n?void 0:n.msg));"error"===t.file.status&&m["a"].error("上传失败")},e}return Object(f["a"])(a,[{key:"avatarUrl",get:function(){return this.user.reavatar?"/file/download/thumb/".concat(this.user.reavatar):"/file/download/thumb/".concat(this.user.avatar)}},{key:"avatarData",get:function(){return{objectName:this.user.avatar||""}}},{key:"beforeUpload",value:function(e){var t="image/jpeg"===e.type||"image/png"===e.type;t||m["a"].error("You can only upload JPG file!");var a=e.size/1024/1024<2;return a||m["a"].error("Image must smaller than 2MB!"),t&&a}},{key:"mounted",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t,a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,S.children();case 2:if(t=e.sent,0===t.code?this.treeData=this.toChildren(t):m["a"].error(t.msg),!this.id){e.next=11;break}return e.next=7,B.find(this.id);case 7:a=e.sent,0===a.code?this.user=a.data:m["a"].error(a.msg),e.next=12;break;case 11:this.rules.password[0].required=!0;case 12:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()},{key:"onLoadData",value:function(e){var t=this;return new Promise((function(a){if(e.dataRef.children)return a(),!1;S.children(e.dataRef.rawId).then((function(n){e.dataRef.children=t.toChildren(n),t.treeData=Object(U["a"])(t.treeData),a(),t.user.deptId=t.user.deptId||0}))}))}},{key:"onSubmit",value:function(){var e=this,t=Object(n["Q"])(this.formRef);t&&t.validate().then(Object(s["a"])(regeneratorRuntime.mark((function t(){var a,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(r=Object(n["N"])(e.user),e.user.id){t.next=7;break}return t.next=4,B.add(r);case 4:a=t.sent,t.next=10;break;case 7:return t.next=9,B.update(r);case 9:a=t.sent;case 10:0===a.code?e.dialog._ok():m["a"].error(a.msg);case 11:case"end":return t.stop()}}),t)})))).catch((function(e){console.log("error",e)}))}},{key:"onCancel",value:function(){this.dialog._close()}},{key:"handleUserStatus",value:function(){this.user.status=1===this.user.status?0:1}},{key:"handleDeptChange",value:function(e,t,a){this.user.deptId=a.triggerNode.dataRef.rawId}},{key:"toChildren",value:function(e){return e.data.map((function(e){return{key:e.id,rawId:e.rawId,type:e.type,value:e.id,title:e.name,code:e.code,isLeaf:2===e.type,selectable:2===e.type}}))}}]),a}(j["b"]);V=Object(h["a"])([Object(j["a"])({components:{UserOutlined:Q["a"],LockOutlined:$["a"],MobileOutlined:q["a"],MailOutlined:J["a"],PlusOutlined:F["a"],LoadingOutlined:W["a"]},props:{id:{type:Number},deptId:{type:Number},deptName:{type:String},dialog:Object}})],V);var Y=V;a("f14e");Y.render=z,Y.__scopeId="data-v-61621a96";var Z=Y,ee=Object(n["l"])("是"),te=Object(n["l"])("否"),ae=Object(n["l"])("提交"),ne=Object(n["l"])("取消");function re(e,t,a,r,c,o){var u=Object(n["K"])("a-input"),i=Object(n["K"])("a-form-item"),l=Object(n["K"])("a-tree-select"),d=Object(n["K"])("a-radio"),s=Object(n["K"])("a-radio-group"),p=Object(n["K"])("a-switch"),f=Object(n["K"])("a-button"),b=Object(n["K"])("a-form");return Object(n["C"])(),Object(n["j"])(b,{ref:"formRef",model:e.formData,rules:e.rules,"label-col":{span:4},"wrapper-col":{span:18},style:{"margin-top":"15px"}},{default:Object(n["U"])((function(){return[Object(n["m"])(i,{label:"节点名称",name:"name"},{default:Object(n["U"])((function(){return[Object(n["m"])(u,{value:e.formData.name,"onUpdate:value":t[1]||(t[1]=function(t){return e.formData.name=t})},null,8,["value"])]})),_:1}),e.isTop?Object(n["k"])("",!0):(Object(n["C"])(),Object(n["j"])(i,{key:0,label:"上级单位",name:"parentId"},{default:Object(n["U"])((function(){return[Object(n["m"])(l,{disabled:!!e.id,"tree-data":e.treeData,"load-data":e.onLoadData,value:e.formData.parentName,"onUpdate:value":t[2]||(t[2]=function(t){return e.formData.parentName=t}),onChange:e.onSelectChange,onTreeExpand:e.onSelectTreeExpand,"tree-default-expand-all":"",style:{width:"100%"},placeholder:"请选择上级单位","dropdown-style":{maxHeight:"400px",overflow:"auto"}},null,8,["disabled","tree-data","load-data","value","onChange","onTreeExpand"])]})),_:1})),e.isTop||1!=e.type?Object(n["k"])("",!0):(Object(n["C"])(),Object(n["j"])(i,{key:1,label:"虚拟节点",name:"virtual"},{default:Object(n["U"])((function(){return[Object(n["m"])(s,{value:e.formData.virtual,"onUpdate:value":t[3]||(t[3]=function(t){return e.formData.virtual=t})},{default:Object(n["U"])((function(){return[Object(n["m"])(d,{value:!0},{default:Object(n["U"])((function(){return[ee]})),_:1}),Object(n["m"])(d,{value:!1},{default:Object(n["U"])((function(){return[te]})),_:1})]})),_:1},8,["value"])]})),_:1})),e.isTop?Object(n["k"])("",!0):(Object(n["C"])(),Object(n["j"])(i,{key:2,label:"节点状态",name:"enabled"},{default:Object(n["U"])((function(){return[Object(n["m"])(p,{checked:e.formData.enabled,"onUpdate:checked":t[4]||(t[4]=function(t){return e.formData.enabled=t})},null,8,["checked"])]})),_:1})),Object(n["m"])(i,{"wrapper-col":{span:14,offset:4}},{default:Object(n["U"])((function(){return[Object(n["m"])(f,{type:"primary",onClick:e.onSubmit},{default:Object(n["U"])((function(){return[ae]})),_:1},8,["onClick"]),Object(n["m"])(f,{style:{"margin-left":"10px"},onClick:e.onCancel},{default:Object(n["U"])((function(){return[ne]})),_:1},8,["onClick"])]})),_:1})]})),_:1},8,["model","rules"])}a("a4d3"),a("b64b");function ce(e,t){if(null==e)return{};var a,n,r={},c=Object.keys(e);for(n=0;n<c.length;n++)a=c[n],t.indexOf(a)>=0||(r[a]=e[a]);return r}function oe(e,t){if(null==e)return{};var a,n,r=ce(e,t);if(Object.getOwnPropertySymbols){var c=Object.getOwnPropertySymbols(e);for(n=0;n<c.length;n++)a=c[n],t.indexOf(a)>=0||Object.prototype.propertyIsEnumerable.call(e,a)&&(r[a]=e[a])}return r}var ue=a("5530"),ie=a("a1e9"),le=a("499d"),de=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){var e;return Object(p["a"])(this,a),e=t.apply(this,arguments),e.treeData=[],e.formData={id:e.id,type:e.type,enabled:!0,virtual:!1,parentId:e.parentId,parentType:e.parentType,parentName:e.parentName},e.formRef=Object(ie["k"])(),e.rules={name:[{required:!0,message:"请输入名称",trigger:"blur"},{max:50,message:"名称最多50个字符"}],parentId:[{required:!0,message:"请选择上级单位",trigger:"blur",type:"number"}]},e}return Object(f["a"])(a,[{key:"isTop",get:function(){return!(this.parentId||this.formData.parentId)}},{key:"mounted",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t,a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,S.loadGroupNodes();case 2:if(t=e.sent,0===t.code){e.next=6;break}return m["a"].error(t.msg),e.abrupt("return",!1);case 6:if(this.treeData=this.toAntTreeNodes(t.data),!this.id){e.next=15;break}return e.next=10,S.find(this.id);case 10:if(a=e.sent,0===a.code){e.next=14;break}return m["a"].error(a.msg),e.abrupt("return",!1);case 14:this.formData=Object(ue["a"])(Object(ue["a"])(Object(ue["a"])({},this.formData),a.data),{},{parentName:a.data.parentType+"_"+a.data.parentId});case 15:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()},{key:"onLoadData",value:function(e){var t=this;return new Promise((function(a){if(e.dataRef.children)return a(),!1;S.children(e.dataRef.rawId).then((function(n){e.dataRef.children=t.toChildren(n),t.treeData=Object(U["a"])(t.treeData),a()}))}))}},{key:"onSelectTreeExpand",value:function(e){console.log(e)}},{key:"onSelectChange",value:function(e,t,a){this.formData.parentId=a.triggerNode.dataRef.rawId,this.formData.parentType=a.triggerNode.dataRef.type}},{key:"onSubmit",value:function(){var e=this,t=Object(ie["u"])(this.formRef);t&&t.validate().then(Object(s["a"])(regeneratorRuntime.mark((function t(){var a,n,r;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:if(e.formData.virtual&&(e.formData.type=0),e.formData.status=e.formData.enabled?1:0,a=Object(ie["p"])(e.formData),a.enabled,a.virtual,a.parentName,n=oe(a,["enabled","virtual","parentName"]),e.id){t.next=12;break}return t.next=6,S.add(n);case 6:return r=t.sent,t.next=9,S.find(r.data);case 9:n=t.sent.data,t.next=15;break;case 12:return t.next=14,S.update(n);case 14:r=t.sent;case 15:0===r.code?e.dialog._ok(n):m["a"].error("错误:".concat(r.msg));case 16:case"end":return t.stop()}}),t)})))).catch((function(e){console.log("error",e)}))}},{key:"onCancel",value:function(){this.dialog._close()}},{key:"toChildren",value:function(e){return e.data.map((function(e){return{key:e.id,rawId:e.rawId,type:e.type,value:e.id,title:e.name,code:e.code,isLeaf:e.type===le["a"].DEPT,selectable:e.type===le["a"].GROUP}}))}},{key:"toAntTreeNodes",value:function(e){var t=this;return e.map((function(e){return{key:e.id,rawId:e.rawId,type:e.type,value:e.id,title:e.name,code:e.code,isLeaf:e.type===le["a"].DEPT,selectable:e.type===le["a"].GROUP,children:t.toAntTreeNodes(e.children)}}))}}]),a}(j["b"]);de=Object(h["a"])([Object(j["a"])({props:{id:{type:Number},type:{type:Number},parentId:{type:Number},parentType:{type:Number},parentName:{type:String},dialog:Object}})],de);var se=de;se.render=re;var pe,fe=se;(function(e){e[e["ADD_USER"]=0]="ADD_USER",e[e["ADD_DEPT"]=1]="ADD_DEPT",e[e["ADD_GROUP"]=2]="ADD_GROUP",e[e["UPDATE_DEPT"]=3]="UPDATE_DEPT",e[e["UPDATE_GROUP"]=4]="UPDATE_GROUP",e[e["DELETE_DEPT"]=5]="DELETE_DEPT",e[e["DELETE_GROUP"]=6]="DELETE_GROUP"})(pe||(pe={}));var be=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){var e;return Object(p["a"])(this,a),e=t.apply(this,arguments),e.treeData=[],e.expandedKeys=[],e.contextMenuKeys=pe,e}return Object(f["a"])(a,[{key:"mounted",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,S.children();case 2:if(t=e.sent,0===t.code){e.next=6;break}return m["a"].error(t.msg),e.abrupt("return",!1);case 6:this.treeData=this.toChildren(t);case 7:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()},{key:"onLoadData",value:function(e){var t=this;return new Promise((function(a){if(e.dataRef.children)return a(),!1;S.children(e.dataRef.rawId).then((function(n){e.dataRef.children=t.toChildren(n),t.treeData=Object(U["a"])(t.treeData),a()}))}))}},{key:"onSelect",value:function(e,t){this.$emit("select",e,t)}},{key:"onContextMenuClick",value:function(e,t){var a,n,r=this,c=t.key;if(pe.ADD_DEPT===c)a="新增部门",n={type:le["a"].DEPT,parentId:e.rawId,parentType:e.type,parentName:e.title};else if(pe.ADD_GROUP===c)a="新增单位",n={type:le["a"].GROUP,parentId:e.rawId,parentType:e.type,parentName:e.title};else if(pe.UPDATE_DEPT===c)a="更新部门",n={id:e.rawId,type:le["a"].DEPT};else{if(pe.UPDATE_GROUP!==c)return pe.DELETE_DEPT===c||pe.DELETE_GROUP===c?(I["a"].confirm({title:"您确定要删除<".concat(e.title,">吗?"),okType:"danger",onOk:function(){S.delete(e.rawId).then((function(t){0===t.code?(m["a"].success("删除成功"),r.loop(r.treeData,e.key,(function(e,t,a){a.splice(t,1)}))):m["a"].error("删除失败:"+t.msg)})).catch((function(e){m["a"].error(e.message)}))}}),!1):pe.ADD_USER===c?(this.$dialog({title:"新增用户",width:550,height:650,max:!1,content:{handle:!0,component:Z,props:{deptId:e.rawId,deptName:e.title}},ok:function(){return r.$emit("select",[e.key],{node:{dataRef:{code:e.code}}}),!0}}),!1):(console.log("unknow menukey ".concat(c)),!1);a="更新单位",n={id:e.rawId,type:le["a"].GROUP}}this.$dialog({title:a,width:550,height:400,content:{handle:!0,props:n,component:fe},ok:function(t){var a=t[0],c="".concat(a.type,"_").concat(a.id),o="".concat(n.parentType,"_").concat(n.parentId),u={id:c,rawId:a.id,type:a.type,name:a.name,code:a.code};return e.key===c?e.dataRef.title=a.name:e.key===o?e.dataRef.children?e.dataRef.children.push(r.dataToTreeDataItem(u)):r.onLoadData(e):r.loop(r.treeData,o,(function(e,t,a){e.children&&e.children.push(r.dataToTreeDataItem(u))})),!0}})}},{key:"watchExpandedKeys",value:function(e,t){console.log("expandedKeys",e,t)}},{key:"toChildren",value:function(e){var t=this;return e.data.map((function(e){return t.dataToTreeDataItem(e)}))}},{key:"dataToTreeDataItem",value:function(e){return{key:e.id,type:e.type,title:e.name,code:e.code,rawId:e.rawId,sort:e.sort,parentId:e.parentId,isLeaf:e.type===le["a"].DEPT}}},{key:"loop",value:function(e,t,a){var n=this;e.forEach((function(e,r,c){return e.key===t?a(e,r,c):e.children?n.loop(e.children,t,a):void 0}))}},{key:"onDrop",value:function(e){if(!e.dropToGap)return!1;var t=e.dragNode.pos;if(e.node.pos.substring(0,e.node.pos.length-1)!==t.substring(0,t.length-1))return!1;var a=e.node.dataRef.key,n=e.dragNode.dataRef.key,r=e.node.pos.split("-"),c=e.dropPosition-Number(r[r.length-1]),o=0,u={};this.loop(this.treeData,n,(function(e,t,a){a.splice(t,1),u=e,o=t}));var i=0,l=[];this.loop(this.treeData,a,(function(e,t,a){l=a,i=t})),-1===c?l.splice(i,0,u):l.splice(i+1,0,u);var d,s={};if(this.loop(this.treeData,n,(function(e,t,a){t<o?s=a[t+1]:t>o&&(s=a[t-1])})),!s.key)return!1;this.loopParentNode(n,{},this.treeData,(function(e){d=e.rawId}));var p={parent:d,sort:u.sort,targetSort:s.sort};return S.dropSort(p).then((function(e){console.log(e.code,e.msg,e.data)})),!1}},{key:"loopParentNode",value:function(e,t,a,n){var r=this;a.forEach((function(a,c,o){if(e===a.key)return n(t);a.children&&r.loopParentNode(e,a,a.children,n)}))}}]),a}(j["b"]);be=Object(h["a"])([Object(j["a"])({emits:["select"]})],be);var Oe=be;Oe.render=_;var he=Oe,me={ascend:"ASC",descend:"DESC"},je=function(e){Object(b["a"])(a,e);var t=Object(O["a"])(a);function a(){var e;return Object(p["a"])(this,a),e=t.apply(this,arguments),e.columns=[{title:"序号",dataIndex:"number",width:100,customRender:function(t){return(e.pageable.curr-1)*e.pageable.size+t.index+1}},{title:"姓名",dataIndex:"name",slots:{filterDropdown:"filterDropdown",filterIcon:"filterIcon",customRender:"name"},onFilterDropdownVisibleChange:function(t){t&&setTimeout((function(){var t=Object(n["Q"])(e.searchInput);t&&t.focus()}),0)}},{title:"账号",dataIndex:"account"},{title:"手机",dataIndex:"phone",width:200},{title:"邮箱",dataIndex:"email",width:200},{title:"更新时间",dataIndex:"updateTime",width:200,sorter:!0,key:"update_time"},{title:"状态",width:100,dataIndex:"status",slots:{customRender:"status"}},{title:"操作",dataIndex:"operation",width:200,slots:{customRender:"operation"}}],e.users=[],e.searchInput=Object(n["I"])(),e.searchText="",e.searchedColumn="",e.pagination={total:200,current:1,pageSize:10},e.pageable={curr:e.pagination.current,size:e.pagination.pageSize,condition:{}},e}return Object(f["a"])(a,[{key:"mounted",value:function(){this.pageQueryUser()}},{key:"pageQueryUser",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,B.pageList(this.pageable);case 2:t=e.sent,0===t.code?(this.users=t.data.data,this.pagination.total=t.data.count):m["a"].error(t.msg);case 4:case"end":return e.stop()}}),e,this)})));function t(){return e.apply(this,arguments)}return t}()},{key:"handleTableChange",value:function(e,t,a){this.pagination.current=(null===e||void 0===e?void 0:e.current)||1,this.pageable.curr=this.pagination.current,this.pageable.sortField=a.columnKey||"",this.pageable.sortOrder=me[a.order]||"",this.pageable.condition.name=t.name instanceof Array?t.name[0]:"",this.pageQueryUser()}},{key:"handleSearch",value:function(e,t,a){t(),this.searchText=e[0]||"",this.searchedColumn=a}},{key:"handleReset",value:function(e){e(),this.searchText=""}},{key:"onUserDel",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,B.delete(t.id);case 2:a=e.sent,0===a.code?this.pageQueryUser():m["a"].error("删除失败:".concat(a.msg));case 4:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}()},{key:"onUserEdit",value:function(e){var t=this;this.$dialog({title:"用户信息",width:550,height:650,max:!1,content:{handle:!0,component:Z,props:{id:e.id,deptId:e.deptId}},ok:function(){return t.pageQueryUser(),!0}})}},{key:"onUserTop",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,B.top(t.id,t.deptId);case 2:a=e.sent,0===a.code?this.pageQueryUser():m["a"].error("置顶失败:".concat(a.msg));case 4:case"end":return e.stop()}}),e,this)})));function t(t){return e.apply(this,arguments)}return t}()},{key:"userStatus",value:function(e){return 1===e.status}},{key:"onUserChange",value:function(){var e=Object(s["a"])(regeneratorRuntime.mark((function e(t){var a;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return t.status=1===t.status?0:1,e.next=3,B.update(t);case 3:a=e.sent,0!==a.code&&(m["a"].error(a.msg),t.status=1===t.status?0:1);case 5:case"end":return e.stop()}}),e)})));function t(t){return e.apply(this,arguments)}return t}()},{key:"onSelectGroup",value:function(e,t){this.pageable.condition.orgcode=t.node.dataRef.code,this.pageQueryUser()}}]),a}(j["b"]);je=Object(h["a"])([Object(j["a"])({components:{Group:he,SearchOutlined:y["a"]}})],je);var ye=je;a("3ec5");ye.render=d,ye.__scopeId="data-v-dd77fd1e";t["default"]=ye},"3ec5":function(e,t,a){"use strict";a("b5fa")},a434:function(e,t,a){"use strict";var n=a("23e7"),r=a("23cb"),c=a("a691"),o=a("50c4"),u=a("7b0b"),i=a("65f0"),l=a("8418"),d=a("1dde"),s=d("splice"),p=Math.max,f=Math.min,b=9007199254740991,O="Maximum allowed length exceeded";n({target:"Array",proto:!0,forced:!s},{splice:function(e,t){var a,n,d,s,h,m,j=u(this),y=o(j.length),v=r(e,y),g=arguments.length;if(0===g?a=n=0:1===g?(a=0,n=y-v):(a=g-2,n=f(p(c(t),0),y-v)),y+a-n>b)throw TypeError(O);for(d=i(j,n),s=0;s<n;s++)h=v+s,h in j&&l(d,s,j[h]);if(d.length=n,a<n){for(s=v;s<y-n;s++)h=s+n,m=s+a,h in j?j[m]=j[h]:delete j[m];for(s=y;s>y-n+a;s--)delete j[s-1]}else if(a>n)for(s=y-n;s>v;s--)h=s+n-1,m=s+a-1,h in j?j[m]=j[h]:delete j[m];for(s=0;s<a;s++)j[s+v]=arguments[s+2];return j.length=y-n+a,d}})},b5fa:function(e,t,a){},c3c4:function(e,t,a){},f14e:function(e,t,a){"use strict";a("c3c4")}}]);
//# sourceMappingURL=chunk-abcdd0b8.2adc37f9.js.map