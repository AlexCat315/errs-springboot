import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: '/index',
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../pages/account/Login.vue'),
        },
        {
            path: "/index",
            name: "index",
            component: () => import('../pages/Index.vue'),
        },
        {
            path: "/register",
            name: "register",
            component: () => import('../pages/account/Register.vue'),
        },

    ]
});

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token') !== null;
    console.log(token);
    if (!token && to.name !== 'login' && to.name!=='register') {
        // 如果未登录，且目标不是登录页，跳转到登录页
        next({ name: 'login' });
    } else if (token && to.name === 'login') {
        // 如果已登录，且目标是登录页，跳转到首页
        next({ name: 'index' });
    } else {
        // 否则直接放行
        next();
    }
});

export default router;
