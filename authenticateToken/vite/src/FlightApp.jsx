import React, { useState, useEffect } from 'react'
import FlightTable from './FlightTable.jsx'
import FlightForm from './FlightForm.jsx'
import Login from './Login.jsx'
import { getAllFlights, addFlight, deleteFlight, getFlightById, updateFlight } from './rest-calls'
import { getToken } from './auth.js'
import './FlightApp.css'

export default function FlightApp() {
    const [flights, setFlights] = useState([])
    const [searchId, setSearchId] = useState('')
    const [searched, setSearched] = useState(null)
    const [loggedIn, setLoggedIn] = useState(!!getToken())

    // load all on mount or when logged in
    useEffect(() => {
        if (loggedIn) refreshList()
    }, [loggedIn])

    function refreshList() {
        getAllFlights()
            .then(setFlights)
            .catch(console.error)
    }

    function handleAdd(f) {
        addFlight(f)
            .then(refreshList)
            .catch(console.error)
    }

    function handleDelete(id) {
        deleteFlight(id)
            .then(refreshList)
            .catch(console.error)
    }

    function handleUpdate(flight) {
        const newDest = window.prompt('New destination:', flight.destination)
        if (newDest == null) return
        const newSeats = window.prompt('New seats:', flight.seats)
        if (newSeats == null) return

        const updated = {
            ...flight,
            destination: newDest,
            seats: Number(newSeats)
        }

        updateFlight(flight.id, updated)
            .then(() => {
                alert('Flight updated')
                refreshList()
            })
            .catch(err => {
                console.error(err)
                alert('Update failed')
            })
    }

    function doSearch() {
        if (!searchId) return
        getFlightById(searchId)
            .then(f => setSearched(f))
            .catch(err => {
                console.error(err)
                alert('Flight not found')
                setSearched(null)
            })
    }

    if (!loggedIn) {
        return <Login onLogin={() => setLoggedIn(true)} />
    }

    return (
        <div className="FlightApp">
            <h1>Flight Management</h1>

            {/* Search by ID */}
            <div style={{ marginBottom: '1rem' }}>
                <input
                    type="number"
                    placeholder="Flight ID"
                    value={searchId}
                    onChange={e => setSearchId(e.target.value)}
                />
                <button onClick={doSearch}>Search</button>
                {searched && (
                    <div>
                        <h3>Search Result:</h3>
                        <FlightTable
                            flights={[searched]}
                            onDelete={handleDelete}
                            onUpdate={handleUpdate}
                        />
                    </div>
                )}
            </div>

            {/* Add Form */}
            <FlightForm onAdd={handleAdd} />

            {/* Table with Delete + Update */}
            <FlightTable
                flights={flights}
                onDelete={handleDelete}
                onUpdate={handleUpdate}
            />
        </div>
    )
}