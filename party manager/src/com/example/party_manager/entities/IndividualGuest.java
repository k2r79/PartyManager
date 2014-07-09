package com.example.party_manager.entities;

public class IndividualGuest extends Guest
{
    private String nom;
    private String prenom;

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }
}
