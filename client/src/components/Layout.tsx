import { Outlet, useLocation } from "react-router-dom"
import Nav from "./Nav"

type User = {
    email: string
    password: string
}

type LayoutProps = {
    loggedInUser: User | null
    setLoggedInUser: React.Dispatch<React.SetStateAction<User | null>>
}

function Layout({loggedInUser, setLoggedInUser}: LayoutProps) {
    const location = useLocation();

    const message = location.state && location.state.message;

    return (
        <div className="container">
            <header className="mb-3">
                <Nav loggedInUser={loggedInUser} setLoggedInUser={setLoggedInUser}/>
            </header>
            <main>
                {message && <p>{message}</p>}
                <Outlet />
            </main>
        </div>
    )
}

export default Layout