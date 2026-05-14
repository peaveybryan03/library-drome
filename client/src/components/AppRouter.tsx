import { useState } from "react"
import Layout from "./Layout"
import Home from "./Home"
import NotFound from "./NotFound"
import { createBrowserRouter, Navigate, RouterProvider } from "react-router-dom"
import UserLayout from "./users/UserLayout"
import UserForm from "./users/UserForm"
import UserLoginForm from "./users/UserLoginForm"

type User = {
    email: string
    password: string
}

function AppRouter() {
    const [loggedInUser, setLoggedInUser] = useState<User | null>(() => {
        const storedUser = localStorage.getItem("loggedInUser")
        return storedUser ? JSON.parse(storedUser) : null
    })

    const routes = [
        {
            path: "",
            element: <Layout loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser}/>,
            children: [
                {
                    path: "/",
                    element: <Home />
                },
                {
                    path: "users",
                    element: <UserLayout />,
                    children: [
                        {
                            path: "add",
                            element: loggedInUser === null ? <UserForm /> : <Navigate to="/" state={{message: "You are already logged in."}} />
                        },
                        {
                            path: "login",
                            element: <UserLoginForm loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser} />
                        }
                    ]
                },
                {
                    path: "*",
                    element: <NotFound />
                }
            ]
        }
    ]

    const router = createBrowserRouter(routes)

    return <RouterProvider router={router} />
}

export default AppRouter