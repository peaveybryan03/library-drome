import { useState } from "react"
import Layout from "./Layout"
import Home from "./Home"
import NotFound from "./NotFound"
import { createBrowserRouter, RouterProvider } from "react-router-dom"

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