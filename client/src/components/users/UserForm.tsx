import { useState } from "react";
import { useNavigate } from "react-router-dom"

type User = {
    email: string
    password: string
}

function UserForm() {
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
        const response = await fetch("http://localhost:8080/api/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user)
        })

        if (response.status >= 200 && response.status < 300) {
            navigate("/")
        } else {
            const payload = await response.json()
            setErrors(payload)
        }
    }

    return (
        <>
            <h2>Sign up for an account</h2>
            <form onSubmit={handleSubmit} >
                {errors.length > 0 && <ul>
                        {errors.map(error => <li key={error}>{error}</li>)}    
                </ul>}

                <div className="form-control">
                        <label htmlFor="email-input">Email: </label>
                        <input type="text" id="email-input" name="email" onChange={handleChange} value={user.email} required />
                    </div>
        
                <div className="form-control">
                    <label htmlFor="password-input">Password: </label>
                    <input type="password" id="password-input" name="password" onChange={handleChange} value={user.password} required />
                </div>
        
                <div className="form-control">
                    <button type="submit">Sign up!</button>
                </div>
            </form>
        </>
    )
}

export default UserForm