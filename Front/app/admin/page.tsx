"use client"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Badge } from "@/components/ui/badge"
import { Input } from "@/components/ui/input"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import {
  Users,
  MessageSquare,
  Coins,
  CheckCircle,
  XCircle,
  Eye,
  Shield,
  CreditCard,
  TrendingUp,
  Star,
} from "lucide-react"

const pendingFeedbacks = [
  {
    id: 1,
    client: "Youssef Mansouri",
    restaurant: "Restaurant Al Fassia",
    rating: 5,
    comment: "Service excellent, ambiance parfaite! Le personnel était très accueillant et la nourriture délicieuse.",
    date: "2024-01-15",
    photos: 2,
    points: 50,
  },
  {
    id: 2,
    client: "Aicha Berrada",
    restaurant: "Café Central",
    rating: 4,
    comment: "Bon café, service rapide.",
    date: "2024-01-15",
    photos: 0,
    points: 25,
  },
]

const users = [
  {
    id: 1,
    name: "Mohamed Benali",
    email: "mohamed@example.com",
    role: "Admin",
    status: "active",
    lastLogin: "2024-01-15",
    totalPoints: 450,
  },
  {
    id: 2,
    name: "Sarah Khalil",
    email: "sarah@example.com",
    role: "Employee",
    status: "active",
    lastLogin: "2024-01-14",
    totalPoints: 0,
  },
  {
    id: 3,
    name: "Youssef Mansouri",
    email: "youssef@example.com",
    role: "E-Client",
    status: "active",
    lastLogin: "2024-01-15",
    totalPoints: 250,
  },
]

const pointsTransactions = [
  {
    id: 1,
    user: "Youssef Mansouri",
    type: "earned",
    amount: 50,
    reason: "Feedback approuvé - Restaurant Al Fassia",
    date: "2024-01-15",
  },
  {
    id: 2,
    user: "Aicha Berrada",
    type: "redeemed",
    amount: -100,
    reason: "Conversion en espèces - 10 MAD",
    date: "2024-01-14",
  },
]

export default function AdminBackoffice() {
  const [activeTab, setActiveTab] = useState("overview")

  const handleApproveFeedback = (id: number) => {
    alert(`Feedback ${id} approuvé!`)
  }

  const handleRejectFeedback = (id: number) => {
    alert(`Feedback ${id} rejeté!`)
  }

  return (
    <div className="flex-1 bg-black p-6">
      {/* Header */}
      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-3xl font-bold text-white">Admin Back-Office</h1>
          <p className="text-gray-400">Gestion complète de la plateforme Clientin</p>
        </div>
        <div className="flex items-center gap-4">
          <Badge className="bg-red-600">Admin</Badge>
          <Avatar>
            <AvatarImage src="/placeholder.svg?height=32&width=32&text=A" />
            <AvatarFallback>AD</AvatarFallback>
          </Avatar>
        </div>
      </div>

      <Tabs value={activeTab} onValueChange={setActiveTab} className="w-full">
        <TabsList className="grid w-full grid-cols-5 bg-gray-800 mb-8">
          <TabsTrigger value="overview" className="text-white">
            Vue d'ensemble
          </TabsTrigger>
          <TabsTrigger value="users" className="text-white">
            Utilisateurs
          </TabsTrigger>
          <TabsTrigger value="moderation" className="text-white">
            Modération
          </TabsTrigger>
          <TabsTrigger value="points" className="text-white">
            Points
          </TabsTrigger>
          <TabsTrigger value="analytics" className="text-white">
            Statistiques
          </TabsTrigger>
        </TabsList>

        <TabsContent value="overview" className="space-y-6">
          {/* KPI Cards */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-400 text-sm">Total Utilisateurs</p>
                    <p className="text-2xl font-bold text-white">1,234</p>
                    <p className="text-green-400 text-sm">+12% ce mois</p>
                  </div>
                  <Users className="h-8 w-8 text-blue-400" />
                </div>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-400 text-sm">Feedbacks Total</p>
                    <p className="text-2xl font-bold text-white">5,678</p>
                    <p className="text-green-400 text-sm">+18% ce mois</p>
                  </div>
                  <MessageSquare className="h-8 w-8 text-purple-400" />
                </div>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-400 text-sm">Points Distribués</p>
                    <p className="text-2xl font-bold text-white">45,230</p>
                    <p className="text-green-400 text-sm">+25% ce mois</p>
                  </div>
                  <Coins className="h-8 w-8 text-yellow-400" />
                </div>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardContent className="p-6">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-gray-400 text-sm">Revenus</p>
                    <p className="text-2xl font-bold text-white">12,450 MAD</p>
                    <p className="text-green-400 text-sm">+8% ce mois</p>
                  </div>
                  <TrendingUp className="h-8 w-8 text-green-400" />
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Recent Activity */}
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Activité Récente</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex items-center gap-3 p-3 bg-gray-800 rounded">
                    <CheckCircle className="h-5 w-5 text-green-500" />
                    <div className="flex-1">
                      <p className="text-white text-sm">Feedback approuvé</p>
                      <p className="text-gray-400 text-xs">Restaurant Al Fassia - Youssef M.</p>
                    </div>
                    <span className="text-gray-400 text-xs">Il y a 5min</span>
                  </div>
                  <div className="flex items-center gap-3 p-3 bg-gray-800 rounded">
                    <Users className="h-5 w-5 text-blue-500" />
                    <div className="flex-1">
                      <p className="text-white text-sm">Nouvel utilisateur</p>
                      <p className="text-gray-400 text-xs">Fatima Alami - E-Client</p>
                    </div>
                    <span className="text-gray-400 text-xs">Il y a 15min</span>
                  </div>
                  <div className="flex items-center gap-3 p-3 bg-gray-800 rounded">
                    <CreditCard className="h-5 w-5 text-yellow-500" />
                    <div className="flex-1">
                      <p className="text-white text-sm">Conversion points</p>
                      <p className="text-gray-400 text-xs">250 points → 30 MAD</p>
                    </div>
                    <span className="text-gray-400 text-xs">Il y a 1h</span>
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Top Restaurants</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-white font-medium">Restaurant Al Fassia</p>
                      <p className="text-gray-400 text-sm">156 feedbacks</p>
                    </div>
                    <Badge className="bg-green-600">4.8★</Badge>
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-white font-medium">Café Central</p>
                      <p className="text-gray-400 text-sm">89 feedbacks</p>
                    </div>
                    <Badge className="bg-green-600">4.6★</Badge>
                  </div>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-white font-medium">Restaurant Marrakech</p>
                      <p className="text-gray-400 text-sm">67 feedbacks</p>
                    </div>
                    <Badge className="bg-yellow-600">4.2★</Badge>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="users" className="space-y-6">
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <div className="flex items-center justify-between">
                <CardTitle className="text-white">Gestion des Utilisateurs</CardTitle>
                <Button className="bg-purple-600 hover:bg-purple-700">Ajouter Utilisateur</Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {users.map((user) => (
                  <div key={user.id} className="flex items-center justify-between p-4 bg-gray-800 rounded-lg">
                    <div className="flex items-center gap-4">
                      <Avatar>
                        <AvatarImage src={`/placeholder.svg?height=40&width=40&text=${user.name.charAt(0)}`} />
                        <AvatarFallback>{user.name.charAt(0)}</AvatarFallback>
                      </Avatar>
                      <div>
                        <p className="text-white font-medium">{user.name}</p>
                        <p className="text-gray-400 text-sm">{user.email}</p>
                      </div>
                    </div>
                    <div className="flex items-center gap-4">
                      <Badge
                        className={
                          user.role === "Admin"
                            ? "bg-red-600"
                            : user.role === "Employee"
                              ? "bg-blue-600"
                              : "bg-green-600"
                        }
                      >
                        {user.role}
                      </Badge>
                      <div className="text-right">
                        <p className="text-white text-sm">{user.totalPoints} pts</p>
                        <p className="text-gray-400 text-xs">Dernière connexion: {user.lastLogin}</p>
                      </div>
                      <div className="flex gap-2">
                        <Button size="sm" variant="outline" className="border-gray-600 text-gray-300 bg-transparent">
                          <Eye className="h-4 w-4" />
                        </Button>
                        <Button size="sm" variant="outline" className="border-gray-600 text-gray-300 bg-transparent">
                          <Shield className="h-4 w-4" />
                        </Button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="moderation" className="space-y-6">
          <Card className="bg-gray-900 border-gray-800">
            <CardHeader>
              <CardTitle className="text-white">Modération des Feedbacks</CardTitle>
            </CardHeader>
            <CardContent>
              <div className="space-y-6">
                {pendingFeedbacks.map((feedback) => (
                  <div key={feedback.id} className="p-6 bg-gray-800 rounded-lg">
                    <div className="flex items-start justify-between mb-4">
                      <div>
                        <h4 className="text-white font-medium">{feedback.restaurant}</h4>
                        <p className="text-gray-400 text-sm">
                          Par {feedback.client} • {feedback.date}
                        </p>
                      </div>
                      <div className="flex items-center gap-2">
                        {[1, 2, 3, 4, 5].map((star) => (
                          <Star
                            key={star}
                            className={`h-4 w-4 ${
                              star <= feedback.rating ? "text-yellow-400 fill-current" : "text-gray-600"
                            }`}
                          />
                        ))}
                      </div>
                    </div>

                    <p className="text-gray-300 mb-4">{feedback.comment}</p>

                    <div className="flex items-center justify-between">
                      <div className="flex items-center gap-4">
                        {feedback.photos > 0 && (
                          <Badge variant="outline" className="text-purple-400 border-purple-400">
                            {feedback.photos} photo(s)
                          </Badge>
                        )}
                        <Badge className="bg-yellow-600">+{feedback.points} points</Badge>
                      </div>
                      <div className="flex gap-2">
                        <Button
                          size="sm"
                          onClick={() => handleRejectFeedback(feedback.id)}
                          className="bg-red-600 hover:bg-red-700"
                        >
                          <XCircle className="h-4 w-4 mr-1" />
                          Rejeter
                        </Button>
                        <Button
                          size="sm"
                          onClick={() => handleApproveFeedback(feedback.id)}
                          className="bg-green-600 hover:bg-green-700"
                        >
                          <CheckCircle className="h-4 w-4 mr-1" />
                          Approuver
                        </Button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="points" className="space-y-6">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Attribution Manuelle de Points</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div>
                  <label className="text-white text-sm font-medium">Utilisateur</label>
                  <Input
                    placeholder="Rechercher un utilisateur..."
                    className="bg-gray-800 border-gray-700 text-white"
                  />
                </div>
                <div>
                  <label className="text-white text-sm font-medium">Points à attribuer</label>
                  <Input type="number" placeholder="Ex: 50" className="bg-gray-800 border-gray-700 text-white" />
                </div>
                <div>
                  <label className="text-white text-sm font-medium">Raison</label>
                  <Input placeholder="Ex: Bonus spécial" className="bg-gray-800 border-gray-700 text-white" />
                </div>
                <Button className="w-full bg-purple-600 hover:bg-purple-700">Attribuer les Points</Button>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Historique des Transactions</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  {pointsTransactions.map((transaction) => (
                    <div key={transaction.id} className="p-3 bg-gray-800 rounded">
                      <div className="flex items-center justify-between mb-1">
                        <span className="text-white font-medium">{transaction.user}</span>
                        <span
                          className={`font-bold ${transaction.type === "earned" ? "text-green-400" : "text-red-400"}`}
                        >
                          {transaction.amount > 0 ? "+" : ""}
                          {transaction.amount} pts
                        </span>
                      </div>
                      <p className="text-gray-400 text-sm">{transaction.reason}</p>
                      <p className="text-gray-500 text-xs">{transaction.date}</p>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>

        <TabsContent value="analytics" className="space-y-6">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Statistiques Globales</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Taux d'approbation</span>
                    <span className="text-white font-semibold">87%</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Temps moyen de modération</span>
                    <span className="text-white font-semibold">2.3h</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Utilisateurs actifs (30j)</span>
                    <span className="text-white font-semibold">892</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Conversion points → argent</span>
                    <span className="text-white font-semibold">23%</span>
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card className="bg-gray-900 border-gray-800">
              <CardHeader>
                <CardTitle className="text-white">Revenus par Mois</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Janvier 2024</span>
                    <span className="text-green-400 font-semibold">12,450 MAD</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Décembre 2023</span>
                    <span className="text-green-400 font-semibold">11,200 MAD</span>
                  </div>
                  <div className="flex justify-between items-center">
                    <span className="text-gray-300">Novembre 2023</span>
                    <span className="text-green-400 font-semibold">9,800 MAD</span>
                  </div>
                  <div className="border-t border-gray-700 pt-3 mt-3">
                    <div className="flex justify-between items-center">
                      <span className="text-white font-medium">Total Q4 2023</span>
                      <span className="text-green-400 font-bold">33,450 MAD</span>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>
      </Tabs>
    </div>
  )
}
