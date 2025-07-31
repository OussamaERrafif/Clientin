"use client"

import type React from "react"

import { useState } from "react"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Button } from "@/components/ui/button"
import { Textarea } from "@/components/ui/textarea"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Checkbox } from "@/components/ui/checkbox"
import { Star, CheckCircle, Smartphone, Upload, MapPin, Clock, User } from "lucide-react"

export default function NFCFeedbackPage() {
  const [rating, setRating] = useState(0)
  const [hoveredRating, setHoveredRating] = useState(0)
  const [comment, setComment] = useState("")
  const [clientName, setClientName] = useState("")
  const [clientEmail, setClientEmail] = useState("")
  const [clientPhone, setClientPhone] = useState("")
  const [visitReason, setVisitReason] = useState("")
  const [serviceType, setServiceType] = useState("")
  const [waitTime, setWaitTime] = useState("")
  const [location, setLocation] = useState("")
  const [isAnonymous, setIsAnonymous] = useState(false)
  const [wouldRecommend, setWouldRecommend] = useState(false)
  const [isSubmitted, setIsSubmitted] = useState(false)
  const [photos, setPhotos] = useState<File[]>([])

  const handlePhotoUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || [])
    setPhotos([...photos, ...files])
  }

  const handleSubmit = () => {
    // Simulate submission
    setIsSubmitted(true)
    setTimeout(() => {
      setIsSubmitted(false)
      setRating(0)
      setComment("")
      setClientName("")
      setClientEmail("")
      setClientPhone("")
      setVisitReason("")
      setServiceType("")
      setWaitTime("")
      setLocation("")
      setIsAnonymous(false)
      setWouldRecommend(false)
      setPhotos([])
    }, 3000)
  }

  if (isSubmitted) {
    return (
      <div className="min-h-screen bg-black flex items-center justify-center p-4">
        <Card className="w-full max-w-md bg-gray-900 border-gray-800">
          <CardContent className="p-8 text-center">
            <div className="mb-6">
              <CheckCircle className="h-16 w-16 text-green-500 mx-auto mb-4 animate-pulse" />
              <h2 className="text-2xl font-bold text-white mb-2">Merci pour votre retour !</h2>
              <p className="text-gray-400">
                Votre feedback a été envoyé avec succès. Nous apprécions votre contribution pour améliorer nos services.
              </p>
            </div>
            <div className="animate-pulse">
              <div className="h-2 bg-green-600 rounded-full"></div>
            </div>
          </CardContent>
        </Card>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-black flex items-center justify-center p-4">
      <Card className="w-full max-w-2xl bg-gray-900 border-gray-800">
        <CardHeader className="text-center">
          <div className="mb-4">
            <Smartphone className="h-12 w-12 text-purple-500 mx-auto mb-2" />
          </div>
          <CardTitle className="text-white text-2xl">Votre Avis Compte</CardTitle>
          <p className="text-gray-400">Partagez votre expérience détaillée avec nous</p>
        </CardHeader>
        <CardContent className="space-y-6">
          {/* Rating */}
          <div className="text-center">
            <Label className="text-white font-medium mb-3 block">Notez votre expérience *</Label>
            <div className="flex justify-center gap-2 mb-2">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  key={star}
                  onClick={() => setRating(star)}
                  onMouseEnter={() => setHoveredRating(star)}
                  onMouseLeave={() => setHoveredRating(0)}
                  className="transition-transform hover:scale-110"
                >
                  <Star
                    className={`h-8 w-8 ${
                      star <= (hoveredRating || rating) ? "text-yellow-400 fill-current" : "text-gray-600"
                    }`}
                  />
                </button>
              ))}
            </div>
            <p className="text-gray-400 text-sm">
              {rating === 0 && "Cliquez sur les étoiles pour noter"}
              {rating === 1 && "Très insatisfait"}
              {rating === 2 && "Insatisfait"}
              {rating === 3 && "Neutre"}
              {rating === 4 && "Satisfait"}
              {rating === 5 && "Très satisfait"}
            </p>
          </div>

          {/* Service Details */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <Label htmlFor="serviceType" className="text-white font-medium mb-2 block">
                <User className="h-4 w-4 inline mr-2" />
                Type de service
              </Label>
              <select
                id="serviceType"
                value={serviceType}
                onChange={(e) => setServiceType(e.target.value)}
                className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white"
              >
                <option value="">Sélectionnez...</option>
                <option value="restaurant">Restaurant</option>
                <option value="livraison">Livraison</option>
                <option value="emporter">À emporter</option>
                <option value="reservation">Réservation</option>
              </select>
            </div>
            <div>
              <Label htmlFor="waitTime" className="text-white font-medium mb-2 block">
                <Clock className="h-4 w-4 inline mr-2" />
                Temps d'attente
              </Label>
              <select
                id="waitTime"
                value={waitTime}
                onChange={(e) => setWaitTime(e.target.value)}
                className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg text-white"
              >
                <option value="">Sélectionnez...</option>
                <option value="0-5min">0-5 minutes</option>
                <option value="5-15min">5-15 minutes</option>
                <option value="15-30min">15-30 minutes</option>
                <option value="30min+">Plus de 30 minutes</option>
              </select>
            </div>
          </div>

          <div>
            <Label htmlFor="visitReason" className="text-white font-medium mb-2 block">
              Raison de votre visite
            </Label>
            <Input
              id="visitReason"
              placeholder="Ex: Dîner d'affaires, célébration, repas familial..."
              value={visitReason}
              onChange={(e) => setVisitReason(e.target.value)}
              className="bg-gray-800 border-gray-700 text-white placeholder-gray-500"
            />
          </div>

          <div>
            <Label htmlFor="location" className="text-white font-medium mb-2 block">
              <MapPin className="h-4 w-4 inline mr-2" />
              Emplacement/Table (optionnel)
            </Label>
            <Input
              id="location"
              placeholder="Ex: Terrasse, Table 12, Salon privé..."
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              className="bg-gray-800 border-gray-700 text-white placeholder-gray-500"
            />
          </div>

          {/* Comment */}
          <div>
            <Label htmlFor="comment" className="text-white font-medium mb-2 block">
              Commentaire détaillé *
            </Label>
            <Textarea
              id="comment"
              placeholder="Décrivez votre expérience: qualité de la nourriture, service, ambiance, propreté, etc. (minimum 20 caractères)"
              value={comment}
              onChange={(e) => setComment(e.target.value)}
              className="bg-gray-800 border-gray-700 text-white placeholder-gray-500 min-h-[120px]"
            />
            <p className="text-gray-400 text-sm mt-1">{comment.length}/20 caractères minimum</p>
          </div>

          {/* Photo Upload */}
          <div>
            <Label className="text-white font-medium mb-2 block">Photos de votre expérience (optionnel)</Label>
            <div className="border-2 border-dashed border-gray-700 rounded-lg p-6 text-center">
              <input
                type="file"
                multiple
                accept="image/*"
                onChange={handlePhotoUpload}
                className="hidden"
                id="photo-upload"
              />
              <label htmlFor="photo-upload" className="cursor-pointer">
                <Upload className="h-8 w-8 text-gray-400 mx-auto mb-2" />
                <p className="text-gray-400">Cliquez pour ajouter des photos</p>
                <p className="text-gray-500 text-sm">JPG, PNG jusqu'à 5MB chacune</p>
              </label>
            </div>
            {photos.length > 0 && (
              <div className="mt-3">
                <p className="text-white text-sm mb-2">{photos.length} photo(s) sélectionnée(s)</p>
                <div className="flex flex-wrap gap-2">
                  {photos.map((photo, index) => (
                    <div key={index} className="bg-gray-800 px-3 py-1 rounded text-sm text-gray-300">
                      {photo.name}
                    </div>
                  ))}
                </div>
              </div>
            )}
          </div>

          {/* Recommendation */}
          <div className="flex items-center space-x-2">
            <Checkbox
              id="recommend"
              checked={wouldRecommend}
              onCheckedChange={(checked) => setWouldRecommend(checked as boolean)}
            />
            <Label htmlFor="recommend" className="text-white">
              Je recommanderais cet établissement à mes amis et famille
            </Label>
          </div>

          {/* Client Information */}
          <div className="border-t border-gray-800 pt-6">
            <div className="flex items-center space-x-2 mb-4">
              <Checkbox
                id="anonymous"
                checked={isAnonymous}
                onCheckedChange={(checked) => setIsAnonymous(checked as boolean)}
              />
              <Label htmlFor="anonymous" className="text-gray-300">
                Rester anonyme
              </Label>
            </div>

            {!isAnonymous && (
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="clientName" className="text-white font-medium mb-2 block">
                    Votre nom
                  </Label>
                  <Input
                    id="clientName"
                    placeholder="Prénom et nom"
                    value={clientName}
                    onChange={(e) => setClientName(e.target.value)}
                    className="bg-gray-800 border-gray-700 text-white placeholder-gray-500"
                  />
                </div>
                <div>
                  <Label htmlFor="clientEmail" className="text-white font-medium mb-2 block">
                    Email (optionnel)
                  </Label>
                  <Input
                    id="clientEmail"
                    type="email"
                    placeholder="votre@email.com"
                    value={clientEmail}
                    onChange={(e) => setClientEmail(e.target.value)}
                    className="bg-gray-800 border-gray-700 text-white placeholder-gray-500"
                  />
                </div>
                <div className="md:col-span-2">
                  <Label htmlFor="clientPhone" className="text-white font-medium mb-2 block">
                    Téléphone (optionnel)
                  </Label>
                  <Input
                    id="clientPhone"
                    type="tel"
                    placeholder="+212 6 XX XX XX XX"
                    value={clientPhone}
                    onChange={(e) => setClientPhone(e.target.value)}
                    className="bg-gray-800 border-gray-700 text-white placeholder-gray-500"
                  />
                </div>
              </div>
            )}
          </div>

          {/* Submit Button */}
          <Button
            onClick={handleSubmit}
            disabled={rating === 0 || comment.length < 20}
            className="w-full bg-purple-600 hover:bg-purple-700 disabled:opacity-50 disabled:cursor-not-allowed py-3 text-lg font-medium"
          >
            Envoyer le Feedback Complet
          </Button>

          <p className="text-center text-gray-500 text-xs">
            Vos données sont sécurisées et utilisées uniquement pour améliorer nos services. En soumettant ce
            formulaire, vous acceptez notre politique de confidentialité.
          </p>
        </CardContent>
      </Card>
    </div>
  )
}
