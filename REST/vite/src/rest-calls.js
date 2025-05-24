import { FLIGHTS_BASE_URL } from './consts.js'

function status(r) {
    if (r.status >= 200 && r.status < 300) return Promise.resolve(r)
    return Promise.reject(new Error(r.statusText))
}

function json(r) {
    return r.json()
}

export function getAllFlights() {
    return fetch(FLIGHTS_BASE_URL, { mode: 'cors' })
        .then(status)
        .then(json)
}

export function getFlightById(id) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, { mode: 'cors' })
        .then(status)
        .then(json)
}

export function addFlight(flight) {
    return fetch(FLIGHTS_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        mode: 'cors',
        body: JSON.stringify(flight)
    })
        .then(status)
        .then(json)
}

export function updateFlight(id, flight) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        mode: 'cors',
        body: JSON.stringify(flight)
    })
        .then(status)
        .then(json)
}

export function deleteFlight(id) {
    return fetch(`${FLIGHTS_BASE_URL}/${id}`, {
        method: 'DELETE',
        mode: 'cors'
    })
        .then(status)
}
