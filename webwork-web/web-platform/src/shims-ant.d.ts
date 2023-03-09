// Ant 组件类型声明
declare namespace AntType {
    interface Form {
        validate(...args: any[]): Promise<any>;
        resetFields(nameList?: NamePath[]): void;
        clearValidate(nameList?: NamePath[]): void;
    }
}
