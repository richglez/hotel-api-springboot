import Navbar from "./components/common/Navbar/Navbar.tsx";
import {Route, Routes} from "react-router-dom";
import Home from "./pages/Home/Home.tsx";
import SignIn from "./pages/auth/SignIn.tsx";
import RoomsPreview from "./pages/Home/sections/RoomsPreview.tsx";
import Booking from "./pages/Book/Booking.tsx";

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