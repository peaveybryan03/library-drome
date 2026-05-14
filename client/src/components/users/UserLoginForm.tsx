import { useState } from "react"
import { useNavigate } from "react-router-dom"

type User = {
    email: string
    password: string
}

type UserLoginFormProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<User | null>>
}

function UserLoginForm({ setLoggedInUser }: UserLoginFormProps) {
    const navigate = useNavigate()

    const [user, setUser] = useState<User>({
        email: "",
        password: ""
    })

    const [errors, setErrors] = useState<string[]>([])

    function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
        setUser({
            ...user,
            [event.target.name]: event.target.value,
        });
    }


    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault()

        const response = await fetch("http://localhost:8080/api/user/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(user)
        })

        const payload = await response.json()

        if (response.status >= 200 && response.status < 300) {
            const userStringAndTotalString = payload.user.split("|")
            const parsedUser = JSON.parse(userStringAndTotalString[0]) 
            parsedUser.diyJwt = payload.user

            localStorage.setItem("loggedInUser", JSON.stringify(parsedUser))
            navigate("/", { state: { message: "You are now logged in" }})
            setLoggedInUser(parsedUser)
        } else {
            setErrors(payload)
        }
    }

    return (
        <>
            <h2>Login to an account</h2>
            <form onSubmit={handleSubmit}>
                {errors.length > 0 && <ul>
                        {errors.map(error => <li key={error}>{error}</li>)}    
                </ul>}

                <div className="form-control">
                    <label htmlFor="email-input">Email: </label>
                    <input type="text" id="email-input" name="email" onChange={handleChange} value={user.email}/>
                </div>

                <div className="form-control">
                    <label htmlFor="password-input">Password: </label>
                    <input type="password" id="password-input" name="password" onChange={handleChange} value={user.password} />
                </div>

                <div className="form-control">
                    <button type="submit">Log in!</button>
                </div>
            </form>
        </>
    )
}

export default UserLoginForm