import React, { useState, useEffect } from 'react'
import FlightTable from './FlightTable.jsx'
import FlightForm from './FlightForm.jsx'
import {
    getAllFlights,
    addFlight,
    deleteFlight,
    getFlightById,
    updateFlight
} from './rest-calls'
import './FlightApp.css'

export default function FlightApp() {
    const [flights, setFlights] = useState([])
    const [searchId, setSearchId] = useState('')
    const [searched, setSearched] = useState(null)

    // load all on mount
    useEffect(() => {
        refreshList()
    }, [])

    function refreshList() {
        getAllFlights().then(setFlights).catch(console.error)
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

    // prompt for new values, call PUT
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

    // search by id
    function doSearch() {
        if (!searchId) return
        getFlightById(searchId)
            .then(f => setSearched(f))
            .catch(err => {
                console.error(err)
                alert('Not found')
                setSearched(null)
            })
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
                        <h3>Found:</h3>
                        <pre>{JSON.stringify(searched, null, 2)}</pre>
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