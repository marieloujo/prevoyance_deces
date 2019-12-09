<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Assurer extends Model
{

    public $table='assures';
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'etat','profession',
        'employeur' ,
    ];

    public function user(){
        return $this->morphOne('App\User','userable');
    }

    public function contrats(){
        return $this->hasMany('App\Models\Contrat');
    }

 
}
