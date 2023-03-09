<template>
    <a-form ref="formRef"
            name="password-form"
            :colon="false"
            :rules="rules"
            :model="formData"
            :label-col="{ span: 4 }"
            :wrapper-col="{ span: 20 }"
            autocomplete="off"
            @finish="handleFinish"
            >
        <a-form-item has-feedback label="原密码" name="password">
            <a-input v-model:value="formData.password" type="password" size="large" placeholder="请输入原密码" />
        </a-form-item>
        <a-form-item has-feedback label="新密码" name="newPassword">
            <a-input v-model:value="formData.newPassword" type="password" size="large" placeholder="请输入新密码"/>
        </a-form-item>
        <a-form-item has-feedback label="确认密码" name="confirmPassword">
            <a-input v-model:value="formData.confirmPassword" type="password" size="large" placeholder="请再次输入新密码"/>
        </a-form-item>
        <a-form-item :wrapper-col="{ span: 20, offset: 4 }" :style="{textAlign: 'right'}">
            <a-space>
                <a-button @click="handleCancel">取消</a-button>
                <a-button type="primary" html-type="submit">确定</a-button>
            </a-space>
        </a-form-item>
    </a-form>
</template>
<script lang="ts">
import { Options, Vue } from 'vue-class-component'
import {
    LockOutlined
} from '@ant-design/icons-vue'
import { Rule } from 'ant-design-vue/es/form/interface'
import { ref } from 'vue'
import request from '@/request/PlatformRequest'
import { message } from 'ant-design-vue'

@Options({
    components: {
        LockOutlined
    },
    emits: ['close']
})
export default class Password extends Vue {
    // 表单对象
    formRef = ref<AntType.Form>()

    // 表单数据
    formData = {
        password: '',
        newPassword: '',
        confirmPassword: ''
    }

    validateNewPassword = async (_rule: Rule, value: string) => {
        if (value === '') {
            return Promise.reject(new Error('请输入新密码'))
        } else {
            if (this.formData.newPassword.length < 6) {
                return Promise.reject(new Error('新密码不能少于6位'))
            }
            if (this.formData.password === value) {
                return Promise.reject(new Error('新密码不能与原密码相同'))
            }
            if (this.formData.confirmPassword !== '') {
                this.formRef.value?.validate('confirmPassword')
            }
            return Promise.resolve()
        }
    }

    validateConfirmPassword = async (_rule: Rule, value: string) => {
        if (value === '') {
            return Promise.reject(new Error('请再次输入新密码'))
        } else if (value !== this.formData.newPassword) {
            return Promise.reject(new Error('新密码两次输入不匹配'))
        } else {
            return Promise.resolve()
        }
    }

    // 校验规则
    rules: Record<string, Rule[]> = {
        password: [{ required: true, message: '请输入原密码' }],
        newPassword: [{ required: true, min: 6, validator: this.validateNewPassword, validateTrigger: 'change' }],
        confirmPassword: [{ required: true, validator: this.validateConfirmPassword, validateTrigger: 'change' }]
    }

    // 取消修改密码
    handleCancel() {
        this.formData = {
            password: '',
            newPassword: '',
            confirmPassword: ''
        }
        this.formRef.value?.resetFields()
        this.formRef.value?.clearValidate()
        this.$emit('close')
    }

    // 确认修改密码
    handleFinish(values: unknown) {
        const data = {
            password: this.formData.password,
            newPassword: this.formData.newPassword
        }
        request.changePassword(data).then(res => {
            if (res.code === 0) {
                message.success('密码修改成功')
                this.handleCancel()
            } else {
                message.error('修改失败: ' + res.msg)
            }
        })
    }
}
</script>
