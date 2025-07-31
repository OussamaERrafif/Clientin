"use client"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { TrendingUp, TrendingDown, Users, MessageSquare, Star } from "lucide-react"
import { InsightChart } from "@/components/insight-chart"

const kpiData = [
  {
    title: "Note Moyenne",
    value: "4.6",
    change: "+0.2",
    trend: "up",
    icon: Star,
    color: "text-yellow-400",
  },
  {
    title: "Taux Positif",
    value: "87%",
    change: "+5%",
    trend: "up",
    icon: TrendingUp,
    color: "text-green-400",
  },
  {
    title: "Utilisateurs Actifs",
    value: "1,234",
    change: "+12%",
    trend: "up",
    icon: Users,
    color: "text-blue-400",
  },
  {
    title: "Total Feedbacks",
    value: "2,847",
    change: "+18%",
    trend: "up",
    icon: MessageSquare,
    color: "text-purple-400",
  },
]

const employeePerformance = [
  { name: "Sarah Khalil", rating: 4.8, feedbacks: 156, trend: "up" },
  { name: "Ahmed Tazi", rating: 4.9, feedbacks: 203, trend: "up" },
  { name: "Mohamed Benali", rating: 4.6, feedbacks: 89, trend: "down" },
  { name: "Fatima Alami", rating: 4.4, feedbacks: 67, trend: "up" },
]

export default function InsightsPage() {
  const [timeRange, setTimeRange] = useState<"day" | "week" | "month">("month")

  return (
    <div className="flex-1 bg-black p-6 min-h-screen">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-3xl font-bold text-white">Insights</h1>
        <div className="flex gap-2">
          {["day", "week", "month"].map((range) => (
            <Button
              key={range}
              variant={timeRange === range ? "default" : "outline"}
              size="sm"
              onClick={() => setTimeRange(range as any)}
              className={
                timeRange === range
                  ? "bg-purple-600 hover:bg-purple-700"
                  : "border-gray-600 text-gray-300 hover:bg-gray-800 bg-transparent"
              }
            >
              {range === "day" ? "Jour" : range === "week" ? "Semaine" : "Mois"}
            </Button>
          ))}
        </div>
      </div>

      <div className="space-y-8 pb-8">
        {/* KPI Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {kpiData.map((kpi, index) => (
            <Card
              key={index}
              className="bg-gray-900 border-gray-800 hover:border-purple-600 transition-colors cursor-pointer"
            >
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-400 text-sm font-medium">{kpi.title}</p>
                    <p className="text-2xl font-bold text-white mt-1">{kpi.value}</p>
                    <div className="flex items-center gap-1 mt-2">
                      {kpi.trend === "up" ? (
                        <TrendingUp className="h-4 w-4 text-green-400" />
                      ) : (
                        <TrendingDown className="h-4 w-4 text-red-400" />
                      )}
                      <span className={`text-sm font-medium ${kpi.trend === "up" ? "text-green-400" : "text-red-400"}`}>
                        {kpi.change}
                      </span>
                      <span className="text-gray-400 text-sm">vs mois dernier</span>
                    </div>
                  </div>
                  <div className={`p-3 rounded-lg bg-gray-800 ${kpi.color}`}>
                    <kpi.icon className="h-6 w-6" />
                  </div>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>

        {/* Charts Section */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Sentiment Trends Chart */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <CardTitle className="text-white">Tendances des Sentiments</CardTitle>
            </CardHeader>
            <CardContent>
              <InsightChart type="sentiment" />
            </CardContent>
          </Card>

          {/* Employee Performance */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <CardTitle className="text-white">Performance par Employé</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {employeePerformance.map((employee, index) => (
                  <div key={index} className="flex items-center justify-between p-3 bg-gray-800 rounded-lg">
                    <div className="flex items-center gap-3">
                      <div className="w-10 h-10 bg-purple-600 rounded-full flex items-center justify-center text-white font-semibold">
                        {employee.name
                          .split(" ")
                          .map((n) => n[0])
                          .join("")}
                      </div>
                      <div>
                        <p className="text-white font-medium">{employee.name}</p>
                        <p className="text-gray-400 text-sm">{employee.feedbacks} feedbacks</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-2">
                      <div className="flex items-center gap-1">
                        <Star className="h-4 w-4 text-yellow-400 fill-current" />
                        <span className="text-white font-semibold">{employee.rating}</span>
                      </div>
                      {employee.trend === "up" ? (
                        <TrendingUp className="h-4 w-4 text-green-400" />
                      ) : (
                        <TrendingDown className="h-4 w-4 text-red-400" />
                      )}
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Additional Charts */}
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          {/* Feedback Volume Chart */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <CardTitle className="text-white">Volume de Feedbacks</CardTitle>
            </CardHeader>
            <CardContent>
              <InsightChart type="volume" />
            </CardContent>
          </Card>

          {/* Response Time Analysis */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <CardTitle className="text-white">Temps de Réponse</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="flex items-center justify-between">
                  <span className="text-gray-300">Temps moyen</span>
                  <span className="text-white font-semibold">2.3h</span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-300">Réponse la plus rapide</span>
                  <span className="text-green-400 font-semibold">15min</span>
                </div>
                <div className="flex items-center justify-between">
                  <span className="text-gray-300">Taux de résolution</span>
                  <span className="text-white font-semibold">94%</span>
                </div>
                <div className="mt-4">
                  <div className="flex justify-between text-sm mb-2">
                    <span className="text-gray-400">Efficacité globale</span>
                    <span className="text-white">87%</span>
                  </div>
                  <div className="w-full bg-gray-700 rounded-full h-2">
                    <div className="bg-purple-600 h-2 rounded-full" style={{ width: "87%" }}></div>
                  </div>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  )
}
