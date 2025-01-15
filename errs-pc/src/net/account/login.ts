import {Post} from '../post'

export function login(LoginForm: any, success: Function, failure: Function) {
    console.log('login', LoginForm.username, LoginForm.password)
    Post('/api/user/account/login', {
        username: LoginForm.username,
        password: LoginForm.password
    }, () => {
        success()
    }, () => {
        failure()
    })
}