<?php

namespace App\Models;

use App\User;
use Illuminate\Database\Eloquent\Model;

class Client extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'profession',
        'employeur' ,
    ];

    public function user(){
        return $this->morphOne('App\User','usereable');
    }

    public function contrats(){
        return $this->hasMany('App\Models\Contrat');
    }

    public function marchands(){
        return $this->belongsToMany('App\Models\Marchand','contrats','client_id','marchand_id')->using('App\Models\Contrat')->withPivot([
            'numero_contrat','garantie','prime','duree','numero_police_assurance','portefeuille','date_debut','date_echeance','date_effet','date_fin','fin','valider','assure_id',
        ])->withTimestamps();
    }

}

