import Navbar from "./shared/components/common/Navbar/Navbar.tsx";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home/Home.tsx";
import SignIn from "./features/auth/pages/SignIn.tsx";
import RoomsPreview from "./features/rooms/pages/RoomsPreview.tsx";
import Booking from "./features/reservations/pages/Booking.tsx";

function App() {
    return (
        <>
            <Navbar/>

            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/rooms" element={<RoomsPreview/>}/>
                <Route path="/booking" element={<Booking/>}/>
                <Route path="/signin" element={<SignIn/>}/>
            </Routes>
        </>
    )
}

export default App;