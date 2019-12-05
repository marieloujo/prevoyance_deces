<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Marchand extends Model
{
    

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'matricule','commission','credit_virtuel','super_marchand_id',
    ];
    
    public function user(){
        return $this->morphOne('App\User','userable');
    }
    
    public function super_marchand(){
        return $this->belongsTo('App\Models\SuperMarchand','super_marchand_id');
    }
    
    public function comptes(){
        return $this->morphMany('App\Models\Compte','compteable');
    }

    public function contrats(){
        return $this->hasMany('App\Models\Contrat');
    }

    public function clients(){
        return $this->belongsToMany('App\Models\Client','contrats','marchand_id','client_id')->using('App\Models\Contrat')->withPivot([
            'numero_contrat','garantie','prime','duree','numero_police_assurance','frais_dossier','date_debut','date_echeance','date_effet','fin','valider','assure_id',
        ])->withTimestamps();
    }


}
