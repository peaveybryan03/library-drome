import { Outlet } from "react-router-dom";

function UserLayout() {
    return (
        <>
            <h3>Users</h3>
            <Outlet />
        </>
    )
}

export default UserLayout