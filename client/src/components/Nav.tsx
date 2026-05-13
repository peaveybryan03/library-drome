import { Link, NavLink } from "react-router-dom"
import logo from "../assets/logo.jpg"

type User = {
    email: string
    password: string
}

type NavProps = {
    loggedInUser: User | null
    setLoggedInUser: React.Dispatch<React.SetStateAction<User | null>>
}

function Nav({loggedInUser, setLoggedInUser}: NavProps) {

    return (
        <nav className="navbar navbar-expand">
            <Link className="navbar-brand" to="/">
                <img src={logo} alt="LibraryDrome" width="150" />
            </Link>
            <ul className="navbar-nav">
                { loggedInUser === null ?
                <>
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/users/add">
                            Sign up
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/users/login">
                            Login
                        </NavLink>
                    </li>
                </>
                :
                <>
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/lists">
                            Lists
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <NavLink id="link" className="nav-link" to="/ratings">
                            Ratings
                        </NavLink>
                    </li>
                    <li className="nav-item">
                        <button id="link" className="nav-link" onClick={() => {
                            setLoggedInUser(null)
                            localStorage.removeItem("loggedInUser")
                        }}>
                            Logout
                        </button>
                    </li>
                </> 
                }
            </ul>
        </nav>
    )
}

export default Nav