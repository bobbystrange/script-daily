#!/usr/bin/env node

const letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

const rand = (a, b) => {
    const offset = b === undefined ? 0 : a
    const width = b === undefined ? a : b - a
    return offset + Math.floor(Math.random() * width)
}

const choose = (list) => {
    const index = rand(list.length)
    return list[index]
}

const repeat = (n, fn) => {
    let s = ""
    const c = Number(n)
    for (let i = 0; i < c; i++) {
        s += fn()
    }
    return s
}

const vendors = [
    "金科婚介所【marry.me】",
    "【唐唐影视】tangtang.tv"
]

const categories = [
    "天外飞仙",
    "黑暗骑士",
    "银河护卫队",
]

const resolutions = [
    "1080pBlueRay",
    "1080pHDTV",
    "720pBlueRay",
    "420P"
]

const formats = [
    "mkv",
    "mp4",
    "avi",
    "rmvb"
]

const fs = require("fs")

for (let k = 0; k < categories.length; k++) {
    const category = categories[k]
    const bound = rand(6, 36)
    for (let i = 1; i <= bound; i++) {
        const edition =  i > 9 ? `${i}` : "0" + i
        let filename = `${category}.E${edition}.${choose(resolutions)}`
        filename += `.${choose(vendors)}.${choose(formats)}`
        const path = `./${filename}`
        fs.writeFile(path, repeat(bound, () => choose(letters)), (err) => {
            if (err) {
                throw err;
            }
        })
    }
}
