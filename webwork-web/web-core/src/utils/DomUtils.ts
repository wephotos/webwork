/** DOM 操作工具类 */
export default class DomUtils {
    /** 获取元素偏移 */
    static offset(elem: HTMLElement) {
        let box = { top: 0, left: 0 }
        const doc = elem && elem.ownerDocument
        const docElem = doc.documentElement
        if (typeof elem.getBoundingClientRect !== 'undefined') {
            // 如果元素有getBoundingClientRect方法
            box = elem.getBoundingClientRect() // 调用该方法
        }
        const win = doc.defaultView
        if (win) {
            return {
                // 元素相对于视窗的距离+滚动距离-边框宽度
                top: box.top + win.pageYOffset - docElem.clientTop,
                left: box.left + win.pageXOffset - docElem.clientLeft
            }
        } else {
            return box
        }
    }

    /**
      * 判断元素是否可见
      * @param el DOM元素
      */
    static isVisible(el: HTMLElement | null): boolean {
        if (el === null) {
            return false
        }
        let loopable = true
        let visible =
            getComputedStyle(el).display !== 'none' &&
            getComputedStyle(el).visibility !== 'hidden'

        while (loopable && visible) {
            el = el && el.parentElement
            if (el && el !== document.body) {
                visible =
                    getComputedStyle(el).display !== 'none' &&
                    getComputedStyle(el).visibility !== 'hidden'
            } else {
                loopable = false
            }
        }

        return visible
    }
}
