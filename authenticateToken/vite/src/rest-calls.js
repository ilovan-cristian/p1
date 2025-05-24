import { FLIGHTS_BASE_URL } from './consts.js'
import { getToken } from './auth.js'

// Helper to check HTTP status
function status(response) {
    if (response.status >= 200 && response.status < 300) return Promise.resolve(response)
    return Promise.reject(new Error(response.statusText))
}

// Helper to parse JSON
function json(response) {
    return response.json()
}

// Build headers including Authorization if token exists
function authHeader(contentType = 'application/json') {
    const token = getToken()
    const headers = { 'Content-Type': contentType }
    if (token) {
        headers['Authorization'] = `Bearer ${token}`
    }
    return headers
}

// Fetch all flights with auth
export function getAllFlights() {
    return fetch(FLIGHTS_BASE_URL, {
        method: 'GET',
        mode: 'cors',
        headers: authHeader()
    })
        .then(status)
        .then(json)
}

// Fetch one flight by ID with auth
export function getFlightById(id) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, {
        method: 'GET',
        mode: 'cors',
        headers: authHeader()
    })
        .then(status)
        .then(json)
}

// Add a new flight with auth
export function addFlight(flight) {
    return fetch(FLIGHTS_BASE_URL, {
        method: 'POST',
        mode: 'cors',
        headers: authHeader(),
        body: JSON.stringify(flight)
    })
        .then(status)
        .then(json)
}

// Update a flight with auth
export function updateFlight(id, flight) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, {
        method: 'PUT',
        mode: 'cors',
        headers: authHeader(),
        body: JSON.stringify(flight)
    })
        .then(status)
        .then(json)
}

// Delete a flight with auth
export function deleteFlight(id) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, {
        method: 'DELETE',
        mode: 'cors',
        headers: authHeader()
    })
        .then(status)
}
