import request from '@/utils/request'

export function getUserInfo() {
    return request({
        url: '/user/info',
        method: 'get'
    })
}

export function updateProfile(data) {
    return request({
        url: '/user/profile',
        method: 'put',
        data
    })
}
