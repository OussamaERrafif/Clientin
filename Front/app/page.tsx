"use client"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog"
import { MoreHorizontal, Calendar, MessageSquare, User, MessageCircle, Star } from "lucide-react"
import { DynamicFeedbackChart } from "@/components/dynamic-feedback-chart"
import { GlobalSearch } from "@/components/global-search"
import { EmployeeModal } from "@/components/employee-modal"
import { ApiTest } from "@/components/api-test"

const feedbackData = [
  {
    id: 1,
    client: "Mohamed B",
    time: "Yesterday, 10:00 AM",
    date: "Auj 10H",
    type: "Avis positif",
    status: "success",
    rating: 5,
    comment:
      "Service excellent, très professionnel et rapide. L'équipe était très accueillante et l'ambiance parfaite. Je recommande vivement ce restaurant à tous mes amis et collègues.",
    restaurant: "Restaurant Al Fassia",
    employee: "Sarah Khalil",
  },
  {
    id: 2,
    client: "Sarah K",
    time: "Yesterday, 4:00 AM",
    date: "Auj 22H",
    type: "Avis positif",
    status: "success",
    rating: 4,
    comment: "Bonne expérience dans l'ensemble, service rapide et nourriture de qualité.",
    restaurant: "Café Central",
    employee: "Ahmed Tazi",
  },
  {
    id: 3,
    client: "Meriem al",
    time: "1 Month Ago, 4:00 PM",
    date: "Hier 14H",
    type: "Avis négatif",
    status: "error",
    rating: 2,
    comment:
      "Service décevant, temps d'attente trop long et personnel peu aimable. La qualité de la nourriture n'était pas à la hauteur de nos attentes.",
    restaurant: "Restaurant Marrakech",
    employee: "Mohamed Benali",
  },
]

const employees = [
  {
    id: 1,
    name: "Alfredo",
    avatar: "/placeholder.svg?height=40&width=40&text=A",
    active: true,
    role: "Manager",
    department: "Service Client",
    email: "alfredo@clientin.com",
    phone: "+212 6 12 34 56 78",
    rating: 4.8,
    feedbackCount: 156,
  },
  {
    id: 2,
    name: "Claudia",
    avatar: "/placeholder.svg?height=40&width=40&text=C",
    active: true,
    role: "Agent",
    department: "Support",
    email: "claudia@clientin.com",
    phone: "+212 6 87 65 43 21",
    rating: 4.6,
    feedbackCount: 89,
  },
  {
    id: 3,
    name: "Canaya",
    avatar: "/placeholder.svg?height=40&width=40&text=Ca",
    active: false,
    role: "Superviseur",
    department: "Qualité",
    email: "canaya@clientin.com",
    phone: "+212 6 11 22 33 44",
    rating: 4.9,
    feedbackCount: 203,
  },
  {
    id: 4,
    name: "Mariana",
    avatar: "/placeholder.svg?height=40&width=40&text=M",
    active: true,
    role: "Agent",
    department: "Service Client",
    email: "mariana@clientin.com",
    phone: "+212 6 55 66 77 88",
    rating: 4.4,
    feedbackCount: 67,
  },
  {
    id: 5,
    name: "Marceline",
    avatar: "/placeholder.svg?height=40&width=40&text=Ma",
    active: true,
    role: "Manager",
    department: "Support",
    email: "marceline@clientin.com",
    phone: "+212 6 99 88 77 66",
    rating: 4.7,
    feedbackCount: 134,
  },
  {
    id: 6,
    name: "Teddy",
    avatar: "/placeholder.svg?height=40&width=40&text=T",
    active: true,
    role: "Agent",
    department: "Qualité",
    email: "teddy@clientin.com",
    phone: "+212 6 33 44 55 66",
    rating: 4.5,
    feedbackCount: 98,
  },
  {
    id: 7,
    name: "Yael",
    avatar: "/placeholder.svg?height=40&width=40&text=Y",
    active: false,
    role: "Superviseur",
    department: "Service Client",
    email: "yael@clientin.com",
    phone: "+212 6 77 88 99 00",
    rating: 4.3,
    feedbackCount: 45,
  },
]

export default function Dashboard() {
  const [feedbackFilter, setFeedbackFilter] = useState<"all" | "positive" | "negative">("all")
  const [selectedFeedback, setSelectedFeedback] = useState<(typeof feedbackData)[0] | null>(null)
  const [selectedEmployee, setSelectedEmployee] = useState<(typeof employees)[0] | null>(null)

  const filteredFeedback = feedbackData.filter((item) => {
    if (feedbackFilter === "positive") return item.status === "success"
    if (feedbackFilter === "negative") return item.status === "error"
    return true
  })

  const renderStars = (rating: number) => {
    return Array.from({ length: 5 }, (_, i) => (
      <Star key={i} className={`h-4 w-4 ${i < rating ? "text-yellow-400 fill-current" : "text-gray-600"}`} />
    ))
  }

  return (
    <div className="flex-1 bg-black p-6 overflow-auto">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-3xl font-bold text-white">Dashboard</h1>
        <div className="flex items-center gap-4">
          <GlobalSearch />
          <Button variant="ghost" size="icon" className="text-gray-400 hover:text-white">
            <MessageCircle className="h-5 w-5" />
          </Button>
          <Button variant="ghost" size="icon" className="text-gray-400 hover:text-white">
            <Calendar className="h-5 w-5" />
          </Button>
          <Avatar>
            <AvatarImage src="/placeholder.svg?height=32&width=32&text=U" />
            <AvatarFallback>U</AvatarFallback>
          </Avatar>
        </div>
      </div>

      {/* RTK Query Test */}
      <div className="mb-6">
        <ApiTest />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Left Column */}
        <div className="lg:col-span-2 space-y-6">
          {/* Feedback Collecté */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader className="flex flex-row items-center justify-between">
              <CardTitle className="text-white">Feedback Collecté</CardTitle>
              <div className="flex gap-2">
                <Button
                  variant={feedbackFilter === "positive" ? "default" : "outline"}
                  size="sm"
                  onClick={() => setFeedbackFilter(feedbackFilter === "positive" ? "all" : "positive")}
                  className={
                    feedbackFilter === "positive"
                      ? "bg-green-600 hover:bg-green-700 text-white border-green-600"
                      : "border-green-600 text-green-600 hover:bg-green-600 hover:text-white bg-transparent"
                  }
                >
                  Positifs
                </Button>
                <Button
                  variant={feedbackFilter === "negative" ? "default" : "outline"}
                  size="sm"
                  onClick={() => setFeedbackFilter(feedbackFilter === "negative" ? "all" : "negative")}
                  className={
                    feedbackFilter === "negative"
                      ? "bg-red-600 hover:bg-red-700 text-white border-red-600"
                      : "border-red-600 text-red-600 hover:bg-red-600 hover:text-white bg-transparent"
                  }
                >
                  Négatifs
                </Button>
                <Button variant="ghost" size="icon" className="text-gray-400">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </div>
            </CardHeader>
          </Card>

          {/* General Feedback Table */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle className="text-white">General</CardTitle>
                <Button variant="ghost" size="icon" className="text-gray-400">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                <div className="grid grid-cols-3 gap-4 text-sm text-gray-400 pb-2 border-b border-gray-800">
                  <div className="flex items-center gap-2">
                    <User className="h-4 w-4" />
                    CLIENT
                  </div>
                  <div className="flex items-center gap-2">
                    <Calendar className="h-4 w-4" />
                    Date
                  </div>
                  <div className="flex items-center gap-2">
                    <MessageSquare className="h-4 w-4" />
                    Type
                  </div>
                </div>
                {filteredFeedback.map((item, index) => (
                  <div
                    key={index}
                    className="grid grid-cols-3 gap-4 py-3 text-sm cursor-pointer hover:bg-gray-800 rounded-lg px-2 transition-colors"
                    onClick={() => setSelectedFeedback(item)}
                  >
                    <div>
                      <div className="text-white font-medium">{item.client}</div>
                      <div className="text-gray-400 text-xs">{item.time}</div>
                    </div>
                    <div className="text-white">{item.date}</div>
                    <div>
                      <Badge
                        variant={item.status === "success" ? "default" : "destructive"}
                        className={
                          item.status === "success" ? "bg-green-600 hover:bg-green-700" : "bg-red-600 hover:bg-red-700"
                        }
                      >
                        {item.type}
                      </Badge>
                      <div className="text-gray-400 text-xs mt-1">success</div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Employé Actif */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle className="text-white">EMPLOYÉ ACTIF</CardTitle>
                <Button variant="ghost" size="icon" className="text-gray-400">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="flex gap-4 overflow-x-auto pb-2">
                {employees.map((employee, index) => (
                  <div
                    key={index}
                    className="flex flex-col items-center gap-2 min-w-fit cursor-pointer hover:bg-gray-800 p-2 rounded-lg transition-colors"
                    onClick={() => setSelectedEmployee(employee)}
                  >
                    <div className="relative">
                      <Avatar className="h-12 w-12">
                        <AvatarImage src={employee.avatar || "/placeholder.svg"} />
                        <AvatarFallback>{employee.name.charAt(0)}</AvatarFallback>
                      </Avatar>
                      <div
                        className={`absolute -bottom-1 -right-1 h-4 w-4 rounded-full border-2 border-gray-900 ${
                          employee.active ? "bg-green-500" : "bg-gray-500"
                        }`}
                      />
                    </div>
                    <span className="text-xs text-gray-300">{employee.name}</span>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Right Column */}
        <div className="space-y-6">
          {/* Abonnement au Service */}
          <Card className="bg-gradient-to-br from-purple-600 to-purple-800 border-purple-600">
            <CardHeader>
              <CardTitle className="text-white">Abonnement au service</CardTitle>
            </CardHeader>
            <CardContent className="space-y-3">
              <div className="text-purple-100">
                <div className="text-sm">123-456-7890</div>
                <div className="text-xs">Type d'abonnement : Mensuelle</div>
                <div className="text-xs">Statut : Actif</div>
                <div className="text-xs">Date d'expiration</div>
                <div className="text-xs">Mars 25/04/2025</div>
                <div className="text-xs">Renouvellement : 25/04/2025</div>
              </div>
              <div className="flex items-center justify-between pt-2">
                <span className="text-white font-medium">Mohamed</span>
                <Badge className="bg-white text-purple-800">CEO</Badge>
              </div>
            </CardContent>
          </Card>

          {/* Feedback Statistique */}
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle className="text-white">Feedback Statistique</CardTitle>
                <Button variant="ghost" size="icon" className="text-gray-400">
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <DynamicFeedbackChart />
            </CardContent>
          </Card>
        </div>
      </div>

      {/* Feedback Detail Modal */}
      {selectedFeedback && (
        <Dialog open={true} onOpenChange={() => setSelectedFeedback(null)}>
          <DialogContent className="max-w-2xl bg-gray-900 border-gray-800">
            <DialogHeader>
              <DialogTitle className="text-white">Détails du Feedback</DialogTitle>
            </DialogHeader>
            <div className="space-y-6">
              <div className="flex items-center justify-between">
                <div>
                  <h3 className="text-xl font-bold text-white">{selectedFeedback.restaurant}</h3>
                  <p className="text-gray-400">
                    Par {selectedFeedback.client} • {selectedFeedback.date}
                  </p>
                </div>
                <Badge className={selectedFeedback.status === "success" ? "bg-green-600" : "bg-red-600"}>
                  {selectedFeedback.type}
                </Badge>
              </div>

              <div className="flex items-center gap-2">
                {renderStars(selectedFeedback.rating)}
                <span className="text-white ml-2">{selectedFeedback.rating}/5</span>
              </div>

              <div>
                <h4 className="text-white font-medium mb-2">Commentaire:</h4>
                <p className="text-gray-300 leading-relaxed">{selectedFeedback.comment}</p>
              </div>

              <div className="grid grid-cols-2 gap-4 pt-4 border-t border-gray-800">
                <div>
                  <span className="text-gray-400">Employé assigné:</span>
                  <p className="text-white font-medium">{selectedFeedback.employee}</p>
                </div>
                <div>
                  <span className="text-gray-400">Heure:</span>
                  <p className="text-white font-medium">{selectedFeedback.time}</p>
                </div>
              </div>
            </div>
          </DialogContent>
        </Dialog>
      )}

      {/* Employee Modal */}
      {selectedEmployee && <EmployeeModal employee={selectedEmployee} onClose={() => setSelectedEmployee(null)} />}

      {/* Floating Service Client Button */}
      <div className="fixed bottom-6 right-6">
        <Button className="bg-purple-600 hover:bg-purple-700 text-white rounded-full px-6 py-3 shadow-lg">
          <MessageCircle className="h-5 w-5 mr-2" />
          Service Client 24H/7J
        </Button>
      </div>
    </div>
  )
}
