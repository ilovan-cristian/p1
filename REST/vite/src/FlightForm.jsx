import React, { useState } from 'react'

export default function FlightForm({ onAdd }) {
    const [location, setLocation] = useState('')
    const [destination, setDestination] = useState('')
    const [departure, setDeparture] = useState('')
    const [seats, setSeats] = useState(0)

    function submit(e) {
        e.preventDefault()
        onAdd({
            // id omitted ⇒ server will generate
            location,
            destination,
            departure,     // ISO string or local datetime
            seats: Number(seats)
        })
        setLocation('')
        setDestination('')
        setDeparture('')
        setSeats(0)
    }

    return (
        <form onSubmit={submit}>
            <label>
                Location:
                <input value={location} onChange={e => setLocation(e.target.value)} />
            </label><br/>
            <label>
                Destination:
                <input value={destination} onChange={e => setDestination(e.target.value)} />
            </label><br/>
            <label>
                Departure:
                <input
                    type="datetime-local"
                    value={departure}
                    onChange={e => setDeparture(e.target.value)}
                />
            </label><br/>
            <label>
                Seats:
                <input
                    type="number"
                    value={seats}
                    onChange={e => setSeats(e.target.value)}
                />
            </label><br/>
            <button type="submit">Add Flight</button>
        </form>
    )
}
