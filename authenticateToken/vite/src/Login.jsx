// Login.jsx
import React, { useState } from 'react'
import { setToken } from './auth.js'

export default function Login({ onLogin }) {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    function login() {
        fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, password })
        })
            .then(res => res.json())
            .then(data => {
                if (data.token) {
                    setToken(data.token)
                    onLogin()
                } else {
                    alert('Login failed')
                }
            })
    }

    return (
        <div>
            <input placeholder="id" onChange={e => setUsername(e.target.value)} />
            <input placeholder="password" type="password" onChange={e => setPassword(e.target.value)} />
            <button onClick={login}>Login</button>
        </div>
    )
}
