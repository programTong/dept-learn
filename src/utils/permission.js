export function getSessionItem(key) {
    return JSON.parse(sessionStorage.getItem(key));
}

export function checkPermission(value) {
    if (value && value instanceof Array && value.length > 0) {
        const user = JSON.parse(localStorage.getItem("user"));
        const roles = user.permissions;
        const permissionRoles = value;

        const hasPermission = roles.some(role => {
            return permissionRoles.includes(role);
        })

        if (!hasPermission) {
            return false;
        }
        return true;
    } else {
        console.error(`need roles! Like v-permission="['admin','editor']"`);
        return false;
    }
}
