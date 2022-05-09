export interface WeatherState {
    id: number;
    name: string;
    description: string;
    iconId: string;
    weatherConditionEnum: string;
    weatherIconUrl: string;
}

export interface Temperature {
    value: number;
    feelsLike: number;
    dewPoint: number;
    unit: string;
}

export interface AtmosphericPressure {
    seaLevelValue: number;
    unit: string;
}

export interface Humidity {
    value: number;
    unit: string;
}

export interface Clouds {
    value: number;
    unit: string;
}

export interface Wind {
    speed: number;
    degrees: number;
    gust: number;
    unit: string;
}

export class Weather {
    forecastTime: Date;
    sunriseTime: Date;
    sunsetTime: Date;
    weatherState: WeatherState;
    temperature: Temperature;
    atmosphericPressure: AtmosphericPressure;
    humidity: Humidity;
    clouds: Clouds;
    uvIndex: number;
    visibilityInMetres: number;
    wind: Wind;
    rain?: any;
    snow?: any;
}

