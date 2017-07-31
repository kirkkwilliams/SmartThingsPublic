/**
 *  Light Up The Night
 *
 *  Author: SmartThings
 */
definition(
    name: "Light Up the Night",
    namespace: "kirkkwilliams",
    author: "kirkkwilliams",
    description: "Turn your lights on when it gets dark and off when it becomes light again.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet-luminance.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_outlet-luminance@2x.png"
)

preferences {
	section("Monitor the luminosity...") {
		input "lightSensor", "capability.illuminanceMeasurement"
 		input("lightLevel", "number", title: "Set Your Lumination trigger (lux):")
	}
	section("Turn on a light...") {
		input "lights", "capability.switch", multiple: true
	}
}

def installed() {
	subscribe(lightSensor, "illuminance", illuminanceHandler)
}

def updated() {
	unsubscribe()
	subscribe(lightSensor, "illuminance", illuminanceHandler)
}

// New aeon implementation
def illuminanceHandler(evt) {
	def lastStatus = state.lastStatus
	if (lastStatus != "on" && evt.integerValue < lightLevel) {
		lights.on()
		state.lastStatus = "on"
	}
	else if (lastStatus != "off" && evt.integerValue > lightLevel) {
		lights.off()
		state.lastStatus = "off"
	}
}
